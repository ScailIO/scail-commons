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

import scala.collection.mutable

class OptionOpsSpec extends Spec {
  "OptionOps should:" - {
    "Execute side-effectful procedures with forNone" in new Context {
      val f = mock[Function0[Unit]]

      Option(expected).forNone(f.apply)
      f.apply wasNever called

      Option.empty[Int].forNone(f.apply)
      f.apply was called
    }

    "Return default empty values" in new Context {
      assert(Option.empty[Boolean].orEmpty == false)
      assert(Option.empty[String].orEmpty == "")
      assert(Option.empty[Byte].orEmpty == (0: Byte))
      assert(Option.empty[Short].orEmpty == (0: Short))
      assert(Option.empty[Int].orEmpty == 0)
      assert(Option.empty[Long].orEmpty == 0L)
      assert(Option.empty[Float].orEmpty == 0.0f)
      assert(Option.empty[Double].orEmpty == 0.0)
      assert(Option.empty[Seq[Int]].orEmpty == Seq.empty[Int])
      assert(Option.empty[mutable.Set[String]].orEmpty == mutable.Set.empty[String])
      assert(Option.empty[Map[Long, Double]].orEmpty == Map.empty[Long, Double])

      type PQ = mutable.PriorityQueue[Int]
      val pq: PQ = Option.empty[PQ].orEmpty
      assert(pq.isEmpty)

      assert(Option(expected).orEmpty == expected)
    }
  }

  class Context {
    // shared objects
    val expected = "forty two"
  }
}
