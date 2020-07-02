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
package scail.commons.ops.collection

import scail.commons.Spec

class MapOpsSpec extends Spec {
  "MapOps should:" - {
    "tell whether the map contains a key-value pair" in new Context {
      assert(target.containsEntry('a', 0))
      assert(!target.containsEntry('b', 2))

      assert(target.containsEntry('a' -> 0))
      assert(!target.containsEntry('b' -> 2))
    }

    "tell whether the map contains at least a key that conforms a predicate" in new Context {
      assert(target.existsKey(_ > 'c'))
      assert(! target.existsKey(_ > 'f'))
    }

    "tell whether the map contains at least a value that conforms to a predicate" in new Context {
      assert(target.existsValue(_ % 2 == 0))
      assert(! target.existsValue(_ > 5))
    }

    "filter values" in new Context {
      val result = target.filterValues(_ % 2 != 0)

      assert(result.size == 2)
      assert(result.contains('b'))
      assert(result.contains('d'))
    }

    "tell whether all keys in the map conform a predicate" in new Context {
      assert(target.forallKey(_.isLetter))
      assert(! target.forallKey(_ < 'd'))
    }

    "tell whether all values in the map conform to a predicate" in new Context {
      assert(target.forallValue(_ < 5))
      assert(! target.forallValue(_ < 4))
    }

    "map keys" in new Context {
      val result = target.mapKeys(_.toUpper)
      assert(result == expected)
    }
  }

  class Context {
    // shared objects
    val target = ('a' to 'e').zipWithIndex.toMap
    val expected = ('A' to 'E').zipWithIndex.toMap
  }
}
