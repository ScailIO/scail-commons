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
package scail.commons.ops.string

import scail.commons.Spec

class ValidatorOpsSpec extends Spec {
  "ValidatorOps should:" - {
    "Validate domains" in {
      assert("example.com".isValidDomain)
      assert(!"banana".isValidDomain)
    }

    "Validate emails" in {
      assert("user@example.com".isValidEmail)
      assert(!"banana".isValidEmail)
    }

    "Validate URLs" in {
      assert("http://example.com".isValidUrl)
      assert(!"banana".isValidUrl)
    }
  }
}
