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

import scail.commons.Spec

import java.io.File

class FileOpsSpec extends Spec {
  "FileOps should:" - {
    "Create new files" in new Context {
      val result = file / name

      assert(result.getName == name)
    }

    "Return a list of file ancestors" in new Context {
      val result = file.ancestors

      assert(result.nonEmpty)
    }

    "Assert a file ends with a given extension" in new Context {
      val result = file / name

      assert(result.hasExtension(ext))
      assert(result.hasExtension('.' + ext))
    }

    "Return a list of path ancestors" in new Context {
      val result = file.path

      assert(result.nonEmpty)
    }
  }

  class Context {
    // shared objects
    val file = new File(".")
    val ext = "txt"
    val name = "test." + ext
  }
}
