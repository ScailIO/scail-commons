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
package scail.commons.ops.collection

import scail.commons.Spec

class TraversableOnceOpsSpec extends Spec {
  "TraversableOnceOps should:" - {
    "Find the largest element" in new Context {
      val result = target.safeMax

      assert(result.contains(3))
      assert(empty.safeMax.isEmpty)
    }

    "Find the smallest element" in new Context {
      val result = target.safeMin

      assert(result.contains(-9))
      assert(empty.safeMin.isEmpty)
    }

    "Find the largest element measured by a function" in new Context {
      val result = target.safeMaxBy(_.abs)

      assert(result.contains(-9))
      assert(empty.safeMaxBy(identity).isEmpty)
    }

    "Find the smallest element measured by a function" in new Context {
      val result = target.safeMinBy(_.abs)

      assert(result.contains(1))
      assert(empty.safeMinBy(identity).isEmpty)
    }
  }

  class Context {
    // shared objects
    val target = Seq(1, 2, 3, -2, -9)
    val empty = Seq.empty[Int]
  }
}
