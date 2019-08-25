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

import scail.commons.Constants.Warts
import scail.commons.Spec

import org.scalatest.concurrent.ScalaFutures

class AnyOpsSpec extends Spec {
  "AnyOps should:" - {
    "Create an already completed Future with value" in new Context with ScalaFutures {
      val res1 = anInt.future.futureValue
      assert(res1 == anInt)

      val res2 = aString.future.futureValue
      assert(res2 == aString)

      val res3 = aSeq.future.futureValue
      assert(res3 == aSeq)
    }

    "Create an Option with value" in new Context {
      val res1 = anInt.option
      assert(res1.value == anInt)

      val res2 = aString.option
      assert(res2.value == aString)

      val res3 = aSeq.option
      assert(res3.value == aSeq)

      @SuppressWarnings(Array(Warts.Null))
      val res4 = (null: String).option // scalastyle:ignore
      assert(res4.isEmpty)
    }

    "Apply side-effectul method to value, returning value" in new Context {
      val f1 = mock[Int => Unit]
      val res1 = anInt tap f1.apply
      assert(res1 == anInt)
      f1(anInt) was called

      val f2 = mock[String => Unit]
      val res2 = aString tap f2
      assert(res2 == aString)
      f2(aString) was called

      val f3 = mock[Seq[Boolean] => Unit]
      val res3 = aSeq tap f3
      assert(res3 == aSeq)
      f3(aSeq) was called
    }
  }

  class Context {
    // shared objects
    val anInt = 123
    val aString = "abc"
    val aSeq = Seq(true, false, true)
  }
}
