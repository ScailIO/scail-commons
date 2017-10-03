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

import org.mindrot.jbcrypt.BCrypt

package object util {
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
}
