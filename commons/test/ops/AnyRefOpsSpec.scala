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

import scail.commons.Constants.Warts
import scail.commons.Spec

class AnyRefOpsSpec extends Spec {
  "AnyRefOps should:" - {
    "Tell whether a reference is null or not" in {
      val target = "abc"
      @SuppressWarnings(Array(Warts.Null))
      val thoseWeDontSpeakOf: AnyRef = null // scalastyle:ignore

      assert(target.nonNull)
      assert(!target.isNull)

      assert(thoseWeDontSpeakOf.isNull)
      assert(!thoseWeDontSpeakOf.nonNull)
    }
  }
}
