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
package scail.commons.i18n

import scail.commons.Constants.Warts
import scail.commons.util.Cache
import scail.commons.util.Key
import scail.commons.util.Logging
import scail.commons.util.NoCache
import scail.commons.util.SimpleCache

import com.ibm.icu.text.MessageFormat
import com.typesafe.config.{Config => TypesafeConfig}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters.mapAsJavaMapConverter

import java.io.File.separator
import java.util.Locale

/**
 * Internationalization (i18n) and localization (l10n) helper class.
 *
 * Message files (located in `resources/i18n/`, by default) are written using
 * [[https://github.com/typesafehub/config Typesafe Config]] "HOCON" format.
 * [[https://github.com/typesafehub/config/blob/master/HOCON.md HOCON]] fully
 * supports UTF-8 Unicode and is more flexible and powerful than Java `.properties`.
 *
 * For instance, it is possible to have a locale such as "en-CA" to automatically
 * default to another locale, say "en", if a message is missing:
 * just add the `include "messages.en.conf"` line to the `messages.en-CA.conf` file.
 *
 * Messages are rendered by [[http://icu-project.org/ ICU4J]], which provides
 * comprehensive support for Unicode, globalization, and internationalization.
 * Compared to Java `MessageFormat`, ICU4J supports named and numbered arguments,
 * enhanced gender and plurals, user-friendly apostrophe quoting syntax,
 * cardinal (''one'', ''two'') and ordinal numbers (''1st'', ''2nd'', ''3rd''),
 * and [[http://site.icu-project.org/home/why-use-icu4j much more]].
 *
 * @see [[http://icu-project.org/apiref/icu4j/com/ibm/icu/text/MessageFormat.html MessageFormat]]
 * @see [[http://icu-project.org/apiref/icu4j/com/ibm/icu/text/PluralFormat.html PluralFormat]]
 * @see [[http://icu-project.org/apiref/icu4j/com/ibm/icu/text/SelectFormat.html SelectFormat]]
 *
 * Optionally, one may use the `@i18n` macro annotation for classes and objects.
 * The `@i18n` macro reads the messages file at compile time and generates typesafe
 * methods for every localized message.
 *
 * @example {{{
 * @i18n object messages
 *
 * implicit val defaultLocale = Locale.ENGLISH
 * val ptBr = Locale.forLanguageTag("pt-BR")
 *
 * assert(messages.helloWorld == "Hello World!")
 * assert(messages.helloWorld(ptBr) == "Oi Mundo!")
 * }}}
 *
 * Formatted messages are also supported:
 *
 * @example {{{
 * assert(messages.hello(name = "Scail") == "Hello Scail!")
 * assert(messages.hello(name = "Scail")(ptBr) == "Oi Scail!")
 * }}}
 *
 * One can pass custom parameters to the annotation by extending the `Messages` class:
 *
 * @example {{{
 * @i18n object messages extends Messages(filename = "sample")
 * }}}
 *
 * Please notice that parameter order in the method follows the same order
 * as they were specified in the messages file.
 * Therefore, it is highly recommended to use named parameters exclusively,
 * as changes in the messages file can easily change parameter names or their relative order.
 *
 * Parameter types are inferred as `java.util.Date` if a date format is used in the messages file,
 * as `java.lang.Number` if a number format is used,
 * and `Any` otherwise.
 */
class Messages(
  folder: String = "i18n"
, filename: String = "messages"
, extension: String = "conf"
, cache: Cache[Locale, TypesafeConfig] = SimpleCache.empty
, formatCache: Cache[KeyLocale, MessageFormat] = NoCache.empty
) extends Logging {
  private def defaultMessage(key: Key)(implicit locale: Locale) = s"[$key:${locale.toLanguageTag}]"

  private def messages(locale: Locale) = {
    cache.getOrElseUpdate(locale, resource(this, locale.toLanguageTag))
  }

  private def format(key: Key)(implicit locale: Locale) = get(key) map { message =>
    formatCache.getOrElseUpdate(KeyLocale(key, locale), new MessageFormat(message, locale))
  }

  private[i18n] def resource(loader: AnyRef, locale: String): TypesafeConfig = {
    val dot = "."
    val root = if (folder.trim.isEmpty) "" else folder + separator
    val ext = if (extension.startsWith(dot)) extension.drop(1) else extension
    val path = Seq(filename, locale, ext).map(_.trim).filterNot(_.isEmpty).mkString(dot)

    ConfigFactory.parseResources(loader.getClass.getClassLoader, root + path)
  }

  /**
   * Optionally returns the localized message identified by `key` for the given `locale`.
   *
   * @param key the key identifying the message
   * @param locale the locale
   * @return the optional value containing the localized message if it exists, `None` otherwise
   */
  def get(key: Key)(implicit locale: Locale): Option[String] = {
    val message = messages(locale)

    if (message.hasPath(key.name)) Option(message.getString(key.name)) else {
      logger.warn(s"Invalid i18n key '$key' for locale '${locale.toLanguageTag}'")
      None
    }
  }

  /**
   * Returns the localized message identified by `key` for the given `locale`.
   *
   * @param key the key identifying the message
   * @param locale the locale
   * @return the localized message if it exists, a default placeholder otherwise
   */
  def apply(key: Key)(implicit locale: Locale): String = get(key) getOrElse defaultMessage(key)

  /**
   * Optionally formats the localized message pattern identified by `key` for the given `locale`
   * using the given arguments `args`.
   *
   * @param key the key identifying the message pattern
   * @param args a map of arguments to be replaced into the pattern
   * @param locale the locale
   * @return the optional value containing the formatted localized message if it exists,
   *         `None` otherwise
   */
  @SuppressWarnings(Array(Warts.Any, Warts.ExposedTuples))
  def get(key: Key, args: (String, Any)*)(implicit locale: Locale): Option[String] = {
    format(key) map (_.format(args.toMap.asJava))
  }

  /**
   * Formats the localized message pattern identified by `key` for the given `locale`
   * using the given arguments `args`.
   *
   * @param key the key identifying the message pattern
   * @param args a map of arguments to be replaced into the pattern
   * @param locale the locale
   * @return the formatted localized message if it exists, a default placeholder otherwise
   */
  @SuppressWarnings(Array(Warts.Any, Warts.ExposedTuples))
  def apply(key: Key, args: (String, Any)*)(implicit locale: Locale): String = {
    get(key, args: _*) getOrElse defaultMessage(key)
  }
}

object Messages {
  /**
   * The current value of the default locale.
   *
   * @return the default locale
   */
  def defaultLocale: Locale = Locale.getDefault
}

final case class KeyLocale(key: Key, locale: Locale)
