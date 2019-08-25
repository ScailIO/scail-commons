/*
 * Copyright 2017-2019 Marconi Lanna
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
package scail.commons

import scail.commons.Constants.Goats
import scail.commons.Constants.Warts

import org.apache.commons.lang3.exception.ExceptionUtils
import org.modeshape.common.text.Inflector.{getInstance => Inflector}

import scala.collection.GenTraversable
import scala.concurrent.Future
import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import scala.util.Try
import scala.util.control.Exception.catching

package object ops {
  /**
   * Extension methods for `Any`.
   */
  implicit class AnyOps[A](private val value: A) extends AnyVal {
    /**
     * Creates an already completed `Future` containing `value`.
     *
     * @return the already completed `Future` with the specified result
     */
    @inline
    def future: Future[A] = Future.successful(value)

    /**
     * Creates an `Option` containing `value`.
     *
     * @return `Some(value)` if `value` is not `null`, `None` otherwise
     */
    @inline
    def option: Option[A] = Option(value)

    /**
     * Applies side-effectul method to `value`, returning `value`.
     *
     * @param f the side-effectful method to apply to `value`
     * @return `value`
     */
    def tap(f: A => Unit): A = {
      f(value)
      value
    }
  }

  /**
   * Extension methods for `AnyRef`.
   */
  implicit class AnyRefOps[A <: AnyRef](private val value: A) extends AnyVal {
    /**
     * Whether a reference is null.
     *
     * @return `true` if null, `false` otherwise
     */
    @inline
    def isNull: Boolean = !nonNull

    /**
     * Whether a reference is non-null.
     *
     * @return `true` if non-null, `false` otherwise
     */
    @inline
    @SuppressWarnings(Array(Goats.NullParameter, Warts.Null))
    def nonNull: Boolean = value != (null: AnyRef) // scalastyle:ignore null
  }

  /**
   * Extension methods for `Boolean`.
   */
  implicit class BooleanOps(private val value: Boolean) extends AnyVal {
    /**
     * Ternary operator.
     *
     * @example {{{
     * cond ? "yes" | "no"
     * }}}
     *
     * @param `true` the value to be returned if the boolean expression is true
     * @tparam A the return type of the expression
     * @return the first parameter if the boolean value is true, the second parameter otherwise
     */
    // scalastyle:off method.name method.argument.name
    def ?[A](`true`: => A): Conditional[A] = new Conditional(value, `true`)
    // scalastyle:on

    /**
     * Converts a boolean value to integer.
     *
     * @return `1` if the boolean is true, `0` otherwise
     */
    @inline
    def asInt: Int = if (value) 1 else 0

    /**
     * Optionally returns a value if the boolean expression is true.
     *
     * @param a the value to be returned as an `Option`
     * @tparam A the type of value `a`
     * @return `Option(a)` if the boolean expression is true, `None` otherwise
     */
    def thenOption[A](a: => A): Option[A] = if (value) Option(a) else None
  }

  /**
   * Extension methods for `Byte`.
   */
  implicit class ByteOps(private val value: Byte) extends AnyVal {
    /**
     * Whether `value` is even.
     *
     * @return `true` if `value` is even, `false` otherwise
     */
    @inline
    def isEven: Boolean = (value & 1) == 0

    /**
     * Whether `value` is odd.
     *
     * @return `true` if `value` is odd, `false` otherwise
     */
    @inline
    def isOdd: Boolean = (value & 1) != 0
  }

  /**
   * Extension methods for `Char`.
   */
  implicit class CharOps(private val value: Char) extends AnyVal {
    /**
     * Whether `value` is even.
     *
     * @return `true` if `value` is even, `false` otherwise
     */
    @inline
    def isEven: Boolean = (value & 1) == 0

    /**
     * Whether `value` is odd.
     *
     * @return `true` if `value` is odd, `false` otherwise
     */
    @inline
    def isOdd: Boolean = (value & 1) != 0
  }

  /**
   * Extension methods for `Enumeration`.
   */
  implicit class EnumerationOps[A <: Enumeration](private val value: A) extends AnyVal {
    /**
     * Typesafe alternative to `Enumeration#withName`.
     *
     * @param s an `Enumeration` name
     * @return the optional `Value` of this `Enumeration` if its name matches `s`, `None` otherwise
     */
    @SuppressWarnings(Array(Warts.EnumerationPartial))
    def byName(s: String): Option[A#Value] = {
      catching[A#Value](classOf[NoSuchElementException]) opt value.withName(s)
    }
  }

  /**
   * Extension methods for `Int`.
   */
  implicit class IntOps(private val value: Int) extends AnyVal {
    /**
     * Whether `value` is even.
     *
     * @return `true` if `value` is even, `false` otherwise
     */
    @inline
    def isEven: Boolean = (value & 1) == 0

    /**
     * Whether `value` is odd.
     *
     * @return `true` if `value` is odd, `false` otherwise
     */
    @inline
    def isOdd: Boolean = (value & 1) != 0

    /**
     * Turns a non-negative number into an ordinal string used to denote
     * the position in an ordered sequence, such as 1st, 2nd, 3rd, 4th.
     *
     * @return the string with the number and ordinal suffix
     */
    @inline
    def ordinalize: String = Inflector.ordinalize(value)
  }

  /**
   * Extension methods for `Long`.
   */
  implicit class LongOps(private val value: Long) extends AnyVal {
    /**
     * Whether `value` is even.
     *
     * @return `true` if `value` is even, `false` otherwise
     */
    @inline
    def isEven: Boolean = (value & 1) == 0

    /**
     * Whether `value` is odd.
     *
     * @return `true` if `value` is odd, `false` otherwise
     */
    @inline
    def isOdd: Boolean = (value & 1) != 0
  }

  /**
   * Extension methods for `Option[A]`.
   */
  implicit class OptionOps[A](private val value: Option[A]) extends AnyVal {
    /**
     * Executes side-effectful procedure `f` if value is empty, do nothing otherwise.
     *
     * @param f the side-effectful procedure to execute
     */
    @inline
    def forNone(f: => Unit): Unit = if (value.isEmpty) f

    /**
     * Returns the option's value if nonempty, otherwise return a default "empty" value.
     *
     * @return the option's value if nonempty, a default empty value otherwise
     */
    def orEmpty(implicit ev: DefaultValue[A]): A = value.getOrElse(ev.default)
  }

  /**
   * Extension methods for `Short`.
   */
  implicit class ShortOps(private val value: Short) extends AnyVal {
    /**
     * Whether `value` is even.
     *
     * @return `true` if `value` is even, `false` otherwise
     */
    @inline
    def isEven: Boolean = (value & 1) == 0

    /**
     * Whether `value` is odd.
     *
     * @return `true` if `value` is odd, `false` otherwise
     */
    @inline
    def isOdd: Boolean = (value & 1) != 0
  }

  /**
   * Extension methods for `Throwable`.
   */
  implicit class ThrowableOps(private val value: Throwable) extends AnyVal {
    /**
     * Typesafe alternative to Throwable#getCause
     *
     * @return the optional cause of the throwable or `None` if the cause is nonexistent or unknown
     */
    @SuppressWarnings(Array(Warts.ThrowablePartial))
    @inline
    def cause: Option[Throwable] = Option(value.getCause)

    /**
     * Returns the stack trace from a `Throwable` as a `String`.
     *
     * @return the stack trace as a `String`
     */
    @inline
    def stackTrace: String = ExceptionUtils.getStackTrace(value)
  }

  /**
   * Extension methods for `Try[A]`.
   */
  implicit class TryOps[A](private val value: Try[A]) extends AnyVal {
    /**
     * Creates an already completed `Future` with the result or exception of this `Try`.
     *
     * @return the already completed `Future` instance with the contents of this `Try`
     */
    @inline
    def toFuture: Future[A] = Future.fromTry(value)
  }

  /**
   * Extension methods for `Tuple2[A, B]`.
   */
  @SuppressWarnings(Array(Warts.ExposedTuples))
  implicit class Tuple2Ops[A, B](private val tuple: Tuple2[A, B]) extends AnyVal {
    /**
     * Alias for `Tuple2#_1`.
     *
     * @return element 1 of this tuple
     */
    @inline
    def key: A = tuple._1

    /**
     * Alias for `Tuple2#_2`.
     *
     * @return element 2 of this tuple
     */
    @inline
    def value: B = tuple._2
  }
}

package ops {
  final class Conditional[A](value: Boolean, `true`: => A) {
    /**
     * Ternary operator.
     *
     * @example {{{
     * cond ? "yes" | "no"
     * }}}
     *
     * @param `false` the value to be returned if the boolean expression is false
     * @return the first parameter if the boolean value is true, the second parameter otherwise
     */
    // scalastyle:off method.name  method.argument.name
    def |(`false`: => A): A = if (value) `true` else `false`
    // scalastyle:on
  }

  /**
   * Typeclass to create default empty values.
   *
   * @tparam A the type of the default empty value
   */
  trait DefaultValue[A] {
    /**
     * Returns a default value for type `A`
     *
     * @return the default value for type `A`
     */
    def default: A
  }

  object DefaultValue {
    /**
     * Factory method to create instances of the `DefaultValue` typeclass.
     *
     * @example {{{
     * implicit val string: DefaultValue[String] = DefaultValue("")
     * }}}
     *
     * @param value the default value
     * @tparam A the type of the default value
     * @return the instance of `DefaultValue[A]`
     */
    def apply[A](value: => A): DefaultValue[A] = new DefaultValue[A] {
      def default: A = value
    }

    /**
     * A default value (`false`) for `Boolean`.
     */
    implicit val boolean: DefaultValue[Boolean] = DefaultValue(false)

    /**
     * A default value (the empty string, `""`) for `String`.
     */
    implicit val string: DefaultValue[String] = DefaultValue("")

    /**
     * Default value (zero) for numeric types.
     */
    implicit def numeric[A: Numeric]: DefaultValue[A] = DefaultValue(implicitly[Numeric[A]].zero)

    /**
     * Default value (as by the companion's `empty` factory method) for collection types.
     */
    implicit def coll[A <: GenTraversable[_]]: DefaultValue[A] = macro collImpl[A]

    def collImpl[A: c.WeakTypeTag](c: blackbox.Context): c.Tree = {
      import c.universe._ // scalastyle:ignore import.grouping underscore.import

      val coll = weakTypeOf[A].dealias
      val tp = coll.typeArgs

      coll.typeSymbol.companion match {
        case NoSymbol => c.abort(c.enclosingPosition, s"Class $coll has no companion object")
        case companion: Symbol => q"DefaultValue($companion.empty[..$tp])"
      }
    }
  }
}
