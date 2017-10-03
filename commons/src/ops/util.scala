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

import org.mindrot.jbcrypt.BCrypt

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
}
