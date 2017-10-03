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

import scail.commons.Constants.Warts
import scail.commons.Spec

import scala.annotation.tailrec

@SuppressWarnings(Array(Warts.Any, Warts.GenTraversableLikeOps, Warts.TraversableOps))
class IndexedSeqOpsSpec extends Spec {
  "IndexedSeqOps" - {
    ".randomElement should:" - {
      "eventually return the first element of the collection" in new Context {
        val target = coll.head
        val tries = draw(target, coll)

        note(s"first element $target found after $tries tries")
      }

      "eventually return the last element of the collection" in new Context {
        val target = coll.last
        val tries = draw(target, coll)

        note(s"last element $target found after $tries tries")
      }

      "eventually return a random element of the collection" in new Context {
        val target = coll.randomElement
        val tries = draw(target, coll)

        note(s"random element $target found after $tries tries")
      }
    }
  }

  class Context {
    // shared objects
    val coll = 1 to 10

    // helper functions
    def draw[A](target: A, coll: IndexedSeq[A]): Int = {
      @tailrec
      def draw(i: Int): Int = if (coll.randomElement == target) i else draw(i + 1)

      draw(1)
    }
  }
}
