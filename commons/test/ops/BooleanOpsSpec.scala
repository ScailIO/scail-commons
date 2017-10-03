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

import scail.commons.Spec

class BooleanOpsSpec extends Spec {
  "BooleanOps should:" - {
    "Evaluate ternary operations" in new Context {
      val res1 = true ? expected | other
      assert(res1 == expected)

      val res2 = false ? expected | other
      assert(res2 == other)
    }

    "Convert boolean to int" in new Context {
      assert(true.asInt == 1)
      assert(false.asInt == 0)
    }

    "Convert boolean to options" in new Context {
      assert(true.thenOption(expected).contains(expected))
      assert(false.thenOption(expected).isEmpty)
    }
  }

  class Context {
    // shared objects
    val expected = "a"
    val other = "b"
  }
}
