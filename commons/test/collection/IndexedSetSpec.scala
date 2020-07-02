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
package scail.commons.collection

import scail.commons.Constants.Warts
import scail.commons.Spec

@SuppressWarnings(Array(Warts.GenTraversableLikeOps, Warts.TraversableOps))
class IndexedSetSpec extends Spec {
  "IndexedSet should:" - {
    "Create empty collection" in new Context {
      val result = IndexedSet.empty(f)

      assert(result.isEmpty)
      assert(result.iterator.isEmpty)
      assert(result.size == 0)
    }

    "Create collection from elements" in new Context {
      val result = IndexedSet(f)(elems: _*)

      assert(result.nonEmpty)
      assert(result.iterator.nonEmpty)
      assert(result.size == elems.size)

      elems foreach { e =>
        f(e) was called
      }
    }

    "Tell whether collection contains element" in new Context {
      val result = IndexedSet(f)(elems: _*)

      elems foreach { e =>
        assert(result.contains(e))
      }

      assert(!result.contains(other))
    }

    "Return collection iterator" in new Context {
      val result: Iterator[String] = IndexedSet(f)(elems: _*).iterator

      assert(result.nonEmpty)
      assert(result.toSet == elems.toSet)
    }

    "Add element to collection" in new Context {
      val result = IndexedSet(f)(elems: _*) + other

      assert(result.size == elems.size + 1)
      assert(result.toSet == elems.toSet + other)
    }

    "Remove element from collection" in new Context {
      val result = IndexedSet(f)(elems: _*) - elems.head

      assert(result.size == elems.size - 1)
      assert(result.toSet == elems.tail.toSet)
    }

    "Iterate through collection" in new Context {
      val result = IndexedSet(f)(elems: _*)

      val g = mock[String => Unit]

      result foreach g

      elems foreach { e =>
        g(e) was called
      }
    }

    "Tell whether collection contains key" in new Context {
      val result = IndexedSet(f)(elems: _*)

      elems foreach { e =>
        assert(result.containsKey(f(e)))
      }

      assert(!result.containsKey(f(other)))
    }

    "Return an element by its key" in new Context {
      val result = IndexedSet(f)(elems: _*)

      assert(result.get(f(elems.head)).contains(elems.head))
      assert(result.get(f(other)).isEmpty)
    }
  }

  class Context {
    // shared objects
    val elems = Seq("a", "bc", "def")
    val other = "ghij"

    // shared mocks
    val f = mock[String => Int]

    // common expectations
    f(any[String]) answers { s: String =>
      s.size
    }
  }
}
