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

import scail.commons.SpecLike

import org.scalatest.freespec.AnyFreeSpec

class IntOpsSpec
  extends AnyFreeSpec
  with SpecLike {
  "IntOps should:" - {
    "Tell whether numbers are even" in {
      assert(0.isEven)
      assert(2.isEven)
      assert((-2).isEven)
      assert(!1.isEven)
      assert(!(-1).isEven)
    }

    "Tell whether numbers are odd" in {
      assert(1.isOdd)
      assert((-1).isOdd)
      assert(!0.isOdd)
      assert(!2.isOdd)
      assert(!(-2).isOdd)
    }

    "Ordinalize numbers" in {
      assert(1.ordinalize == "1st")
      assert(2.ordinalize == "2nd")
      assert(3.ordinalize == "3rd")
      assert(4.ordinalize == "4th")
      assert(5.ordinalize == "5th")
      assert(10.ordinalize == "10th")
      assert(11.ordinalize == "11th")
      assert(12.ordinalize == "12th")
      assert(13.ordinalize == "13th")
      assert(14.ordinalize == "14th")
      assert(21.ordinalize == "21st")
      assert(22.ordinalize == "22nd")
      assert(23.ordinalize == "23rd")
      assert(24.ordinalize == "24th")
    }
  }
}
