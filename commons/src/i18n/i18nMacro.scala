/*
 * Copyright 2017-2020 Marconi Lanna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scail.commons.i18n

import scail.commons.Constants.Goats
import scail.commons.Constants.Warts

import com.ibm.icu.text.MessagePattern
import com.ibm.icu.text.MessagePattern.ArgType.SIMPLE
import com.ibm.icu.text.MessagePattern.Part.Type.ARG_START
import com.typesafe.config.ConfigObject
import com.typesafe.config.ConfigValue
import com.typesafe.config.ConfigValueType.STRING

import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly
import scala.collection.JavaConverters.mapAsScalaMapConverter
import scala.collection.breakOut
import scala.collection.mutable
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

/**
 * @see [[scail.commons.i18n.Messages]]
 */
@SuppressWarnings(Array(
  Goats.ClassNames, Goats.NullParameter, Goats.UnusedMethodParameter, Warts.Null))
@compileTimeOnly("enable macro paradise to expand macro annotations")
class i18n extends StaticAnnotation { // scalastyle:ignore class.name
  def macroTransform(annottees: Any*): Any = macro i18nMacro.impl
}

/**
 * @see [[scail.commons.i18n.Messages]]
 */
// scalastyle:off multiple.string.literals
@SuppressWarnings(Array(Goats.ClassNames, Goats.FinalModifierOnCaseClass, Warts.Any))
class i18nMacro(val c: blackbox.Context) { // scalastyle:ignore class.name
  import c.universe._ // scalastyle:ignore import.grouping underscore.import

  case class Expansion(parents: Seq[Tree], body: Tree)
  case class Parameter(name: String, tpe: Type)

  @SuppressWarnings(Array(Warts.OldTime))
  object types {
    val any = definitions.AnyTpe
    val anyref = tq"scala.AnyRef"
    val date = typeOf[java.util.Date]
    val locale = typeOf[java.util.Locale]
    val messages = typeOf[Messages]
    val number = typeOf[java.lang.Number]
  }

  val locale = Seq(q"implicit val locale: ${types.locale}")

  def parentMessages(parents: Seq[Tree]): Option[Messages] = {
    def extendsMessages(name: Tree) = c.typecheck(name, c.TYPEmode).tpe =:= types.messages

    parents.headOption collect {
      case q"${tq"$name[..$targs]"}(...$argss)" if extendsMessages(name) =>
        val code = c.Expr[Messages](q"new ${types.messages}[..$targs](...$argss)")
        c.eval(code)
    }
  }

  def expandConfig(config: ConfigObject, prefix: String = ""): Tree = {
    val members = config.asScala.map { case (key, value) =>
      value match {
        case obj: ConfigObject => expandObject(prefix, key, obj)
        case str: ConfigValue if str.valueType == STRING => expandString(prefix, key, str)
        case _ => message(prefix, key)
      }
    }
    q"..$members"
  }

  def expandObject(prefix: String, key: String, config: ConfigObject): Tree = {
    q"""object ${TermName(key)} { ..${expandConfig(config, prefix + key + ".")}}"""
  }

  def expandString(prefix: String, key: String, value: ConfigValue): Tree = {
    val args = mutable.LinkedHashMap.empty[String, Set[Type]]
    val parts = new MessagePattern(value.render)

    def add(name: String, tpe: Option[Type]) = {
      args(name) = args.getOrElse(name, Set.empty) ++ tpe.toList
    }

    def part(i: Int) = parts.getSubstring(parts.getPart(i))

    def resolveArgType(s: String): Option[Type] = Option(s) collect {
      case "number" | "spellout" | "ordinal" | "duration" => types.number
      case "date" | "time" => types.date
    }

    val params: Seq[Parameter] = if (!parts.hasNamedArguments) Seq.empty[Parameter] else {
      (0 until parts.countParts).map(parts.getPartType).zipWithIndex foreach {
        case (ARG_START, i) =>
          val name = part(i + 1)
          parts.getPart(i).getArgType match {
            case SIMPLE => add(name, resolveArgType(part(i + 2)))
            case _ => add(name, None)
          }
        case _ =>
      }

      args.map { case (name, typeSet) =>
        val tpe = typeSet.to[Seq] match {
          case Seq() => types.any
          case Seq(tpe) => tpe
          case _ => c.abort(c.enclosingPosition
            , s"i18n key '$prefix$key' is being used as both a date and a number")
        }
        Parameter(name, tpe)
      }(breakOut)
    }

    message(prefix, key, params)
  }

  def message(prefix: String, key: String, params: Seq[Parameter] = Seq.empty[Parameter]): Tree = {
    val defParams: Seq[Tree] = params map { p =>
      q"${TermName(p.name)}: ${p.tpe}"
    }

    val appParams: Seq[Tree] = params map { p =>
      q"${p.name} -> ${TermName(p.name)}"
    }

    val paramss = Seq(defParams, locale).filterNot(_.isEmpty)
    val path = q"${prefix + key}"

    q"def ${TermName(key)}(...$paramss): String = apply(..${path +: appParams})(locale)"
  }

  def expand(parents: Seq[Tree]): Expansion = {
    val messages = parentMessages(parents)
    val config = messages.getOrElse(new Messages).resource(this, "").root

    val augmentedParents = {
      if (messages.isDefined) parents
      else q"${types.messages}" +: parents.filterNot(types.anyref.equalsStructure)
    }

    Expansion(augmentedParents, expandConfig(config))
  }

  def impl(annottees: Tree*): Tree = {
    // scalastyle:off line.size.limit
    annottees match {
      case q"$mods class $name[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$body }" :: tail => // linter:ignore UndesirableTypeInference
        val expansion = expand(parents)
        val companion = tail collect { case tree: Tree => tree }
        q"""
          $mods class $name[..$tparams] $ctorMods(...$paramss)
            extends { ..$earlydefns }
            with ..${expansion.parents} { $self =>
            ..$body
            ..${expansion.body}
          }
          ..$companion
        """
      case q"$mods object $name extends { ..$earlydefns } with ..$parents { $self => ..$body }" :: Nil => // linter:ignore UndesirableTypeInference
        val expansion = expand(parents)
        q"""
          $mods object $name
            extends { ..$earlydefns }
            with ..${expansion.parents} { $self =>
            ..$body
            ..${expansion.body}
          }
        """
      case _ => c.abort(c.enclosingPosition
        , "Invalid annottee: only classes or objects can be annottated with @i18n")
    }
    // scalastyle:on line.size.limit
  }
}
