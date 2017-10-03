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
package scail.commons.ops.string

import scail.commons.Spec

class DigestOpsSpec extends Spec {
  "DigestOps should:" - {
    "Compute message digests" in new Context {
      assert(target.md2 == expected.md2)
      assert(target.md5 == expected.md5)
      assert(target.sha1 == expected.sha1)
      assert(target.sha256 == expected.sha256)
      assert(target.sha384 == expected.sha384)
      assert(target.sha512 == expected.sha512)
    }
  }

  class Context {
    // shared objects
    val target = "The quick brown fox jumps over the lazy dog"

    object expected {
      val md2 = "03d85a0d629d2c442e987525319fc471"
      val md5 = "9e107d9d372bb6826bd81d3542a419d6"
      val sha1 = "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12"
      val sha256 = "d7a8fbb307d7809469ca9abcb0082e4f8d5651e46d3cdb762d02d0bf37c9e592"
      val sha384 = "ca737f1014a48f4c0b6dd43cb177b0afd9e5169367544c49" +
        "4011e3317dbf9a509cb1e5dc1e85a941bbee3d7f2afbc9b1"
      val sha512 = "07e547d9586f6a73f73fbac0435ed76951218fb7d0c8d788a309d785436bbb64" +
        "2e93a252a954f23912547d1e8a3b5ed6e1bfd7097821233fa0538f3db854fee6"
    }
  }
}
