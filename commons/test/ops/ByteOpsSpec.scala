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

import scail.commons.Spec

class ByteOpsSpec extends Spec {
  "ByteOps should:" - {
    "Tell whether numbers are even" in {
      assert((0: Byte).isEven)
      assert((2: Byte).isEven)
      assert((-2: Byte).isEven)
      assert(!(1: Byte).isEven)
      assert(!(-1: Byte).isEven)
    }

    "Tell whether numbers are odd" in {
      assert((1: Byte).isOdd)
      assert((-1: Byte).isOdd)
      assert(!(0: Byte).isOdd)
      assert(!(2: Byte).isOdd)
      assert(!(-2: Byte).isOdd)
    }
  }
}
