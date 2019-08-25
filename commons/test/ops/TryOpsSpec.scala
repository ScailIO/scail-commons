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

import scala.util.Try

class TryOpsSpec extends Spec {
  "TryOps should:" - {
    "Return an already completed Future with the Try's content" in new Context {
      val res1 = succ.toFuture
      assert(res1.isCompleted)
      assert(res1.value.value.success.value == expected)

      val res2 = fail.toFuture
      assert(res2.isCompleted)
      assert(res2.value.value.failure.exception.toString == cause.toString)
    }
  }

  @SuppressWarnings(Array(Warts.Throw))
  class Context {
    // shared objects
    val cause = new ArithmeticException
    val expected = 42

    val succ = Try(expected)
    val fail = Try[Int](throw cause)
  }
}
