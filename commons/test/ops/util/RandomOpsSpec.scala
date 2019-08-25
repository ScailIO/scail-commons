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

import scail.commons.Constants.Warts
import scail.commons.Spec

import scala.annotation.tailrec
import scala.util.Random

@SuppressWarnings(Array(Warts.Any))
class RandomOpsSpec extends Spec {
  "RandomOps should:" - {
    "Generate random values between two numbers" in new Context {
      def random: Int = Random.between(a, b)

      val res1 = draw(a, random)
      note(s"first element $a found after $res1 tries")

      val res2 = draw(b, random)
      note(s"last element $b found after $res2 tries")

      val target = random
      val res3 = draw(target, random)
      note(s"random element $target found after $res3 tries")
    }

    "Generate strings of pseudorandom alphanumeric characters" in new Context {
      val result = Random.nextAlphanumeric(length)

      assert(result.length == length)
      assert(result.forall(_.isLetterOrDigit))
    }

    "Generate strings of pseudorandom alphabetic characters" in new Context {
      val result = Random.nextAlphabetic(length)

      assert(result.length == length)
      assert(result.forall(_.isLetter))
    }
  }

  class Context {
    // shared objects
    val a = 1
    val b = 6
    val length = 42

    // helper functions
    def draw[A](target: A, f: => A): Int = {
      @tailrec
      def draw(i: Int): Int = if (f == target) i else draw(i + 1)

      draw(1)
    }
  }
}
