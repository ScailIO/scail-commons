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
package scail.commons.util

import scail.commons.Constants.Warts
import scail.commons.Spec

@SuppressWarnings(Array(Warts.Throw))
class UtilSpec extends Spec {
  "defaultsTo should:" - {
    "evaluate exp if no exception is thrown" in {
      val expected = 42

      def exp: Int = expected

      val result = defaultsTo(0) {
        exp
      }

      assert(result == expected)
    }

    "ignore non-fatal exception and return default value" in {
      def exp: Int = throw new ArithmeticException

      val expected = 42

      val result = defaultsTo(expected) {
        exp
      }

      assert(result == expected)
    }

    "not catch fatal exception" in {
      def exp: Int = throw new StackOverflowError

      assertThrows[StackOverflowError] {
        defaultsTo(0) {
          exp
        }
      }
    }
  }
}
