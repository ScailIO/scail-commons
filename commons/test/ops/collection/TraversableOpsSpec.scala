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

class TraversableOpsSpec extends Spec {
  "TraversableOps should:" - {
    "Find duplicate elements" in {
      val target = Seq(1, 2, 3, 2, 5, 4, 5, 3, 2)
      val expected = Seq(2, 3, 5)

      val result = target.duplicates

      assert(result.sorted == expected)
    }

    "Find duplicate elements according to a transformation function" in {
      val target = -2 to 5
      val expected = Seq(1, 2)

      val result = target.duplicatesBy(_.abs)

      assert(result.sorted == expected)
    }

    "Filter elements by type" in {
      trait A
      trait B extends A
      trait C extends B
      trait D

      object A extends A
      object B extends B
      object C extends C
      object D extends D
      object E extends B with D
      object F extends D with B

      val target = Seq(A, B, C, D, E, F)
      val expected = Seq(B, C, E, F)

      val result = target.filterByType[B]

      assert(result == expected)
    }
  }
}
