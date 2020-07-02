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

class EscapeOpsSpec extends Spec {
  "EscapeOps should:" - {
    "Escape CSV" in new Context {
      val result = unescaped.csv.escapeCsv

      assert(result == escaped.csv)
    }

    "Escape HTML" in new Context {
      val result = unescaped.html.escapeHtml

      assert(result == escaped.html)
    }

    "Escape Java" in new Context {
      val result = unescaped.java.escapeJava

      assert(result == escaped.java)
    }

    "Escape Json" in new Context {
      val result = unescaped.json.escapeJson

      assert(result == escaped.json)
    }

    "Escape XML" in new Context {
      val result = unescaped.xml.escapeXml

      assert(result == escaped.xml)
    }

    "Unescape CSV" in new Context {
      val result = escaped.csv.unescapeCsv

      assert(result == unescaped.csv)
    }

    "Unescape HTML" in new Context {
      val result = escaped.html.unescapeHtml

      assert(result == unescaped.html)
    }

    "Unescape Java" in new Context {
      val result = escaped.java.unescapeJava

      assert(result == unescaped.java)
    }

    "Unescape Json" in new Context {
      val result = escaped.json.unescapeJson

      assert(result == unescaped.json)
    }

    "Unescape XML" in new Context {
      val result = escaped.xml.unescapeXml

      assert(result == unescaped.xml)
    }
  }

  class Context {
    // shared objects
    object unescaped {
      val csv = ","
      val html = "\""
      val java = "\t\n\"\\"
      val json = "\n"
      val xml = "\""
      val regex = "*"
      val regexQuote = "$"
    }

    object escaped {
      val csv = "\",\""
      val html = "&quot;"
      val java = "\\t\\n\\\"\\\\"
      val json = "\\n"
      val xml = "&quot;"
      val regex = "\\*"
      val regexQuote = "\\$"
    }
  }
}
