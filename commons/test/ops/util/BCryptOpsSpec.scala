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
package scail.commons.ops.util

import scail.commons.Spec

class BCryptOpsSpec extends Spec {
  "BCryptOps should:" - {
    "Generate salt" in new Context {
      val res1 = BCryptOps.salt
      assert(res1.size == saltSize)

      val res2 = BCryptOps.salt(rounds)
      assert(res2.size == saltSize)
    }

    "Hash password" in new Context {
      val res1 = target.bcrypt
      assert(res1.size == hashSize)

      val res2 = target.bcrypt(rounds)
      assert(res2.size == hashSize)

      val res3 = target.bcrypt(salt)
      assert(res3.size == hashSize)
    }

    "Verify password hash match" in new Context {
      val hash = target.bcrypt

      assert(target.bcryptMatches(hash))
    }
  }

  class Context {
    // shared objects
    val target = "forty two"

    val rounds = 4
    val salt = BCryptOps.salt(rounds)

    val saltSize = 29
    val hashSize = 60
  }
}
