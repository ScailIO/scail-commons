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
package scail.commons.ops

import scail.commons.Constants.Goats
import scail.commons.Constants.Warts

import com.typesafe.scalalogging.Logger

import org.mindrot.jbcrypt.BCrypt

import scala.util.Random

import java.io.File

package object util {
  private val extSep = "."

  /**
   * Extension methods for bcrypt password hashing.
   */
  implicit class BCryptOps(private val value: String) extends AnyVal {
    /**
     * Hashes a password using the OpenBSD bcrypt scheme.
     *
     * @return the hashed password
     */
    @inline
    def bcrypt: String = BCrypt.hashpw(value, BCryptOps.salt)

    /**
     * Hashes a password using the OpenBSD bcrypt scheme.
     *
     * @param rounds the log2 of the number of rounds of hashing to apply.
     *               The work factor increases as 2^`rounds`^.
     * @return the hashed password
     */
    @inline
    def bcrypt(rounds: Int): String = BCrypt.hashpw(value, BCrypt.gensalt(rounds))

    /**
     * Hashes a password using the OpenBSD bcrypt scheme.
     *
     * @param salt the salt to hash with (generated using `BCryptOps.salt`)
     * @return the hashed password
     */
    @inline
    def bcrypt(salt: String): String = BCrypt.hashpw(value, salt)

    /**
     * Whether a plaintext password matches a previously hashed one.
     *
     * @param hash the previously-hashed password
     * @return true if the passwords match, false otherwise
     */
    @inline
    def bcryptMatches(hash: String): Boolean = BCrypt.checkpw(value, hash)
  }

  object BCryptOps {
    private val rounds = 10

    /**
     * Generates a salt for the `bcrypt` method using the default number of 2^10^ hashing rounds.
     *
     * @return the encoded salt value
     */
    @inline
    def salt: String = BCrypt.gensalt(rounds)

    /**
     * Generates a salt for the `bcrypt` method using 2^`rounds`^ hashing rounds.
     *
     * @param rounds the log2 of the number of rounds of hashing to apply.
     *               The work factor increases as 2^`rounds`^.
     * @return the encoded salt value
     */
    @inline
    def salt(rounds: Int): String = BCrypt.gensalt(rounds)
  }

  /**
   * Extension methods for `File`.
   */
  implicit class FileOps(private val value: File) extends AnyVal {
    /**
     * Creates a new `File` instance from a parent `File` and a child pathname string.
     *
     * @param file the child pathname string
     * @return the new `File` instance
     */
    // scalastyle:off method.name
    @inline
    def /(file: String): File = new File(value, file)
    // scalastyle:on

    /**
     * Returns a lazy list containing a `File` ancestors.
     *
     * @return the lazy list containing all ancestors
     */
    @SuppressWarnings(Array(Goats.NullParameter, Warts.Null))
    def ancestors: Stream[File] = {
      Stream.iterate(value)(_.getParentFile).takeWhile(_ != null) // scalastyle:ignore null
    }

    /**
     * Whether a file ends with a given extension.
     * A leading dot (`"."`) is automatically inserted if it doesn't exist already.
     *
     * @param extension the file extension optionally including the dot, e.g. `".jpg"`
     * @return `true` if the file ends with `ext`, `false` otherwise
     */
    @inline
    def hasExtension(extension: String): Boolean = {
      val ext = if (extension startsWith extSep) extension else extSep + extension
      value.getName.endsWith(ext)
    }

    /**
     * Returns a list of path ancestors split by the OS-specific path separator.
     *
     * @return the list of path ancestors
     */
    def path: Seq[String] = value.getCanonicalPath.split(File.separatorChar).to[Seq]
  }

  private val exceptionMessage = "exception:"
  /**
   * Extension methods for Scala Logging.
   */
  implicit class LoggerOps(private val value: Logger) extends AnyVal {
    /**
     * Logs an exception (throwable) at the ERROR level.
     *
     * @param e the exception (throwable) to log
     */
    @inline
    def error(e: => Throwable): Unit = value.error(exceptionMessage, e)

    /**
     * Logs an exception (throwable) at the WARN level.
     *
     * @param e the exception (throwable) to log
     */
    @inline
    def warn(e: => Throwable): Unit = value.warn(exceptionMessage, e)

    /**
     * Logs an exception (throwable) at the INFO level.
     *
     * @param e the exception (throwable) to log
     */
    @inline
    def info(e: => Throwable): Unit = value.info(exceptionMessage, e)

    /**
     * Logs an exception (throwable) at the DEBUG level.
     *
     * @param e the exception (throwable) to log
     */
    @inline
    def debug(e: => Throwable): Unit = value.debug(exceptionMessage, e)

    /**
     * Logs an exception (throwable) at the TRACE level.
     *
     * @param e the exception (throwable) to log
     */
    @inline
    def trace(e: => Throwable): Unit = value.trace(exceptionMessage, e)
  }

  /**
   * Extension methods for `scala.util.Random`.
   */
  implicit class RandomOps(private val value: Random) extends AnyVal {
    /**
     * Returns a pseudorandom, uniformly distributed integer value between `a` and `b`, inclusive.
     *
     * @param a the first value in the range, inclusive
     * @param b the last value in the range, inclusive
     * @return the pseudorandom, uniformly distributed integer between `a` and `b`, inclusive
     */
    def between(a: Int, b: Int): Int = value.nextInt((a - b).abs + 1) + a.min(b)

    /**
     * Generates a string of given length of pseudorandomly alphanumeric characters,
     * equally chosen from A-Z, a-z, and 0-9.
     *
     * @param length the desirable length of the genrerated pseudorandom string
     * @return the pseudorandom alphanumeric string of length `length`
     */
    def nextAlphanumeric(length: Int): String = value.alphanumeric.take(length).mkString

    /**
     * Generates a string of given length of pseudorandomly alphabetic characters,
     * equally chosen from A-Z and a-z.
     *
     * @param length the desirable length of the genrerated pseudorandom string
     * @return the pseudorandom alphabetic string of length `length`
     */
    def nextAlphabetic(length: Int): String = {
      value.alphanumeric.filter(_.isLetter).take(length).mkString
    }
  }
}
