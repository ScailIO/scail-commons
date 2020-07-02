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
package scail.commons.ops

import scail.commons.Constants.Warts

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.text.StringEscapeUtils
import org.apache.commons.text.WordUtils
import org.apache.commons.validator.routines.DomainValidator.{getInstance => DomainValidator}
import org.apache.commons.validator.routines.EmailValidator.{getInstance => EmailValidator}
import org.apache.commons.validator.routines.UrlValidator.{getInstance => UrlValidator}
import org.modeshape.common.text.Inflector.{getInstance => Inflector}

import scala.util.control.Exception.catching

import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.io.Reader
import java.io.StringReader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.{UTF_8 => utf8}
import java.security.MessageDigest
import java.util.regex.Matcher
import java.util.regex.Pattern

package object string {
  private val truthy = Set("y", "yes", "t", "true", "on")
  private val falsy = Set("n", "no", "f", "false", "off")

  /**
   * Extension methods for `String`.
   */
  @SuppressWarnings(Array(Warts.StringOpsPartial))
  implicit class StringOps(private val value: String) extends AnyVal {
    /**
     * Creates a new `File` instance from this value and a child pathname string.
     *
     * @param pathname the child pathname, either a directory or a file
     * @return the new `File` instance from this input text and a child pathname string
     */
    // scalastyle:off method.name
    @inline
    def /(pathname: String): File = new File(value, pathname)
    // scalastyle:on

    /**
     * Typesafe alternative to `toBoolean`.
     *
     * @return if the input text is parseable as boolean, an option containing it; `None` otherwise
     */
    def asBoolean: Option[Boolean] = {
      catching[Boolean](classOf[IllegalArgumentException]) opt value.toBoolean
    }

    private def parse[A](f: String => A) = catching[A](classOf[NumberFormatException]) opt f(value)

    /**
     * Typesafe alternative to `toByte`.
     *
     * @return if the input text is parseable as byte, an option containing it; `None` otherwise
     */
    def asByte: Option[Byte] = parse(_.toByte)

    /**
     * Typesafe alternative to `toShort`.
     *
     * @return if the input text is parseable as short, an option containing it; `None` otherwise
     */
    def asShort: Option[Short] = parse(_.toShort)

    /**
     * Typesafe alternative to `toInt`.
     *
     * @return if the input text is parseable as int, an option containing it; `None` otherwise
     */
    def asInt: Option[Int] = parse(_.toInt)

    /**
     * Typesafe alternative to `toLong`.
     *
     * @return if the input text is parseable as long, an option containing it; `None` otherwise
     */
    def asLong: Option[Long] = parse(_.toLong)

    /**
     * Typesafe alternative to `toFloat`.
     *
     * @return if the input text is parseable as float, an option containing it; `None` otherwise
     */
    def asFloat: Option[Float] = parse(_.toFloat)

    /**
     * Typesafe alternative to `toDouble`.
     *
     * @return if the input text is parseable as double, an option containing it; `None` otherwise
     */
    def asDouble: Option[Double] = parse(_.toDouble)

    /**
     * Creates a `InputStream` backed by this input text.
     *
     * @param cs the `Charset` to be used to encode the input text
     * @return the `InputStream` backed by this input text
     */
    @inline
    def asInputStream(cs: Charset = utf8): InputStream = {
      new ByteArrayInputStream(value.getBytes(cs))
    }

    /**
     * Creates a `InputStream` backed by this input text using the UTF-8 charset.
     *
     * @return the `InputStream` backed by this input text
     */
    @inline
    def asInputStream: InputStream = value.asInputStream(utf8)

    /**
     * Creates a `Reader` backed by this input text.
     *
     * @return the `Reader` backed by this input text
     */
    @inline
    def reader: Reader = new StringReader(value)

    /**
     * Compares two strings for equality using a constant time simple byte compare.
     *
     * @param that the string to compare to
     * @param cs the `CharSet` to be used for the comparison (defaults to UTF-8)
     * @return true if both strings are equal, false otherwise
     */
    @inline
    def constantTimeEquals(that: String, cs: Charset = utf8): Boolean = {
      MessageDigest.isEqual(value.getBytes(cs), that.getBytes(cs))
    }

    /**
     * Alias for `constantTimeEquals`.
     */
    @inline
    def safeEquals(that: String, cs: Charset = utf8): Boolean = constantTimeEquals(that, cs)

    /**
     * Checks whether the input text is empty (`""`), null or whitespace-only.
     *
     * @return true if the input text is null, empty or whitespace only, false otherwise
     */
    @inline
    def isBlank: Boolean = StringUtils.isBlank(value)

    /**
     * Checks whether the input text is not empty (`""`), not null and not whitespace-only.
     *
     * @return true if the input text is not null, empty or whitespace-only, false otherwise
     */
    @inline
    def nonBlank: Boolean = StringUtils.isNotBlank(value)

    /**
     * Whether the input text is one of "n", "no", "f", "false", "off".
     * Case and leading and trailing whitespace do not matter.
     *
     * @return true if the input text is falsy, false otherwise
     */
    @inline
    def isFalsy: Boolean = falsy(value.trim.toLowerCase)

    /**
     * Whether the input text is one of "y", "yes", "t", "true", "on".
     * Case and leading and trailing whitespace do not matter.
     *
     * @return true if the input text is truthy, false otherwise
     */
    @inline
    def isTruthy: Boolean = truthy(value.trim.toLowerCase)

    /**
     * Removes leading and trailing whitespace and replaces sequences of whitespace by a single one.
     *
     * @return the input text with leading, trailing and consecutive whitespace removed
     */
    @inline
    def normalizeSpace: String = StringUtils.normalizeSpace(value)

    /**
     * Removes diacritics (accents) from the input text.
     *
     * @return the input text with diacritics removed
     */
    @inline
    def stripAccents: String = StringUtils.stripAccents(value)

    /**
     * Converts all the whitespace separated words into capitalized words, that is each
     * word is made up of a title case character and then a series of lowercase characters.
     * Capitalization uses the Unicode title case, normally equivalent to upper case.
     *
     * @return the input text capitalized to title case
     */
    @inline
    def titleCase: String = WordUtils.capitalizeFully(value)
  }

  /**
   * Extension methods for message digests.
   */
  implicit class DigestOps(private val value: String) extends AnyVal {
    /**
     * Calculates the MD2 digest and returns the value as a 32 character hex string.
     *
     * @return the MD2 digest as a hex string
     */
    @inline
    def md2: String = DigestUtils.md2Hex(value)

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @return the MD5 digest as a hex string
     */
    @inline
    def md5: String = DigestUtils.md5Hex(value)

    /**
     * Calculates the SHA-1 digest and returns the value as a 40 character hex string.
     *
     * @return the SHA-1 digest as a hex string
     */
    @inline
    def sha1: String = DigestUtils.sha1Hex(value)

    /**
     * Calculates the SHA-256 digest and returns the value as a 64 character hex string.
     *
     * @return the SHA-256 digest as a hex string
     */
    @inline
    def sha256: String = DigestUtils.sha256Hex(value)

    /**
     * Calculates the SHA-384 digest and returns the value as a 96 character hex string.
     *
     * @return the SHA-384 digest as a hex string
     */
    @inline
    def sha384: String = DigestUtils.sha384Hex(value)

    /**
     * Calculates the SHA-512 digest and returns the value as a 128 character hex string.
     *
     * @return the SHA-512 digest as a hex string
     */
    @inline
    def sha512: String = DigestUtils.sha512Hex(value)
  }

  /**
   * Extension methods for string escaping and unescaping.
   */
  implicit class EscapeOps(private val value: String) extends AnyVal {
    /**
     * Returns a value for a CSV column enclosed in double quotes, if required, as per RFC4180.
     *
     * @return the input CSV column string
     */
    @inline
    def escapeCsv: String = StringEscapeUtils.escapeCsv(value)

    /**
     * Escapes the characters in a string using HTML entities.
     * Supports all known HTML 4.0 entities, including accents.
     *
     * @return the HTML4 escaped string
     */
    @inline
    def escapeHtml: String = StringEscapeUtils.escapeHtml4(value)

    /**
     * Escapes the characters in a string using Java string rules.
     *
     * @return the Java escaped string
     */
    @inline
    def escapeJava: String = StringEscapeUtils.escapeJava(value)

    /**
     * Escapes the characters in a string using Json string rules.
     *
     * @return the Json escaped string
     */
    @inline
    def escapeJson: String = StringEscapeUtils.escapeJson(value)

    /**
     * Escapes the characters in a string using XML entities.
     *
     * @return the XML escaped string
     */
    @inline
    def escapeXml: String = StringEscapeUtils.escapeXml11(value)

    /**
     * Returns a string value for an unescaped CSV column.
     *
     * @return the unescaped string
     */
    @inline
    def unescapeCsv: String = StringEscapeUtils.unescapeCsv(value)

    /**
     * Unescapes a string containing HTML 4.0 entity escapes to the
     * actual Unicode characters corresponding to the escapes.
     *
     * @return the unescaped string
     */
    @inline
    def unescapeHtml: String = StringEscapeUtils.unescapeHtml4(value)

    /**
     * Unescapes any Java literals found in the string.
     *
     * @return the unescaped string
     */
    @inline
    def unescapeJava: String = StringEscapeUtils.unescapeJava(value)

    /**
     * Unescapes any Json literals found in the string.
     *
     * @return the unescaped string
     */
    @inline
    def unescapeJson: String = StringEscapeUtils.unescapeJson(value)

    /**
     * Unescapes a string containing XML entity escapes to the
     * actual Unicode characters corresponding to the escapes.
     *
     * @return the unescaped string
     */
    @inline
    def unescapeXml: String = StringEscapeUtils.unescapeXml(value)

    /**
     * Produces a string that can be used as a `Pattern` that matches
     * the original string as if it were a literal pattern.
     *
     * @return the literal string pattern
     */
    @inline
    def quoteRegex: String = Pattern.quote(value)

    /**
     * Produces a string that can be used as a literal replacement
     * in the `appendReplacement` method of the `Matcher` class.
     *
     * @return the literal string replacement pattern
     */
    @inline
    def quoteRegexReplacement: String = Matcher.quoteReplacement(value)
  }

  /**
   * Extension methods for inflection (singular/plural) and naming case convention transformations.
   */
  implicit class InflectorOps(private val value: String) extends AnyVal {
    /**
     * Capitalizes the first word and turns underscores into spaces and
     * strips trailing "_id" and any supplied removable tokens.
     *
     * @return the humanized string
     */
    @inline
    def humanize: String = humanize()

    /**
     * Capitalizes the first word and turns underscores into spaces and
     * strips trailing "_id" and any supplied removable tokens.
     *
     * @param removableTokens optional array of tokens that are to be removed
     * @return the humanized string
     */
    @inline
    def humanize(removableTokens: String*): String = Inflector.humanize(value, removableTokens: _*)

    /**
     * Capitalizes all the words and replaces some characters in the string
     * to create a nicer looking title.
     * Underscores are changed to spaces, a trailing "_id" is removed,
     * and any of the supplied tokens are removed.
     *
     * @return the title-case version of the supplied string
     */
    @inline
    def humanizeTitle: String = humanizeTitle()

    /**
     * Capitalizes all the words and replaces some characters in the string
     * to create a nicer looking title.
     * Underscores are changed to spaces, a trailing "_id" is removed,
     * and any of the supplied tokens are removed.
     *
     * @param removableTokens optional array of tokens that are to be removed
     * @return the title-case version of the supplied string
     */
    @inline
    def humanizeTitle(removableTokens: String*): String = {
      Inflector.titleCase(value, removableTokens: _*)
    }

    /**
     * Converts strings to lowerCamelCase.
     *
     * @return the lower camel case version of the word
     */
    @inline
    def lowerCamelCase: String = lowerCamelCase("")

    /**
     * Converts strings to lowerCamelCase.
     * This method will also use any extra delimiter characters to identify word boundaries.
     *
     * @param delimiterChars optional characters that are used to delimit word boundaries
     * @return the lower camel case version of the word
     */
    @inline
    def lowerCamelCase(delimiterChars: String): String = {
      Inflector.lowerCamelCase(value.toLowerCase, delimiterChars.toCharArray: _*)
    }

    /**
     * Returns the plural form of the word.
     *
     * @return the pluralized form of the word
     */
    @inline
    def pluralize: String = pluralize(2)

    /**
     * Returns the plural form of the word.
     *
     * @param count the number to pluralize to
     * @return the pluralized form of the word, or the word itself if it could not be pluralized
     *         or if `count` was either 1 or -1
     */
    @inline
    def pluralize(count: Int): String = Inflector.pluralize(value, count)

    /**
     * Returns a copy of the input with the first character converted to uppercase and
     * the remainder to lowercase.
     *
     * @return the word with the first character capitalized and the remaining characters lowercased
     */
    @inline
    def sentenceCase: String = Inflector.capitalize(value)

    /**
     * Returns the singular form of the word.
     *
     * @return the singularized form of the word, or the word itself if it could not be singularized
     */
    @inline
    def singularize: String = Inflector.singularize(value)

    /**
     * Makes an underscored form from the expression in the string,
     * the reverse of the camelCase method.
     *
     * @return the lower-cased version of the input with words delimited by the underscore character
     */
    @inline
    def underscore: String = underscore("")

    /**
     * Makes an underscored form from the expression in the string,
     * the reverse of the camelCase method.
     * Also changes any characters that match the supplied delimiters into underscore.
     *
     * @param delimiterChars optional characters that are used to delimit word boundaries
     * @return the lower-cased version of the input with words delimited by the underscore character
     */
    @inline
    def underscore(delimiterChars: String): String = {
      Inflector.underscore(value, delimiterChars.toCharArray: _*)
    }

    /**
     * Converts strings to UpperCamelCase.
     *
     * @return the upper camel case version of the word
     */
    @inline
    def upperCamelCase: String = upperCamelCase("")

    /**
     * Converts strings to UpperCamelCase.
     * This method will also use any extra delimiter characters to identify word boundaries.
     *
     * @param delimiterChars optional characters that are used to delimit word boundaries
     * @return the upper camel case version of the word
     */
    @inline
    def upperCamelCase(delimiterChars: String): String = {
      Inflector.upperCamelCase(value.toLowerCase, delimiterChars.toCharArray: _*)
    }
  }

  /**
   * Extension methods for string validation.
   */
  implicit class ValidatorOps(private val value: String) extends AnyVal {
    /**
     * Whether `value` is as a valid domain name with a recognized top-level domain.
     *
     * @return `true` if `value` is a valid domain name, `false` otherwise
     */
    @inline
    def isValidDomain: Boolean = DomainValidator.isValid(value)

    /**
     * Whether `value` is a valid e-mail address.
     *
     * @return `true` if `value` is a valid email address, `false` otherwise
     */
    @inline
    def isValidEmail: Boolean = EmailValidator.isValid(value)

    /**
     * Whether `value` is a valid URL address.
     *
     * @return `true` if `value` is a valid URL address, `false` otherwise
     */
    @inline
    def isValidUrl: Boolean = UrlValidator.isValid(value)
  }
}
