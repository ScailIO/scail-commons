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

class InflectorOpsSpec extends Spec {
  "InflectorOps should:" - {
    "Humanize" in new Context {
      assert(underscore.humanize == simple.capitalize)

      val result = target.humanize(token)
      val expected = "Abcdef"

      assert(result == expected)
    }

    "Humanize title" in new Context {
      assert(simple.humanizeTitle == title)

      val result = target.humanizeTitle(token)
      val expected = "Abcdef"

      assert(result == expected)
    }

    "Lower camel case" in new Context {
      assert(underscore.lowerCamelCase == lowerCamel)

      val result = target.lowerCamelCase(token)
      val expected = "abcDef"

      assert(result == expected)
    }

    "Pluralize" in new Context {
      assert(singular.pluralize == plural)
      assert(singular.pluralize(0) == plural)
      assert(singular.pluralize(1) == singular)
      assert(singular.pluralize(2) == plural)
    }

    "Sentence Case" in new Context {
      assert(simple.sentenceCase == simple.capitalize)
    }

    "Singularize" in new Context {
      assert(plural.singularize == singular)
    }

    "Underscore" in new Context {
      assert(lowerCamel.underscore == underscore)

      val result = target.underscore(token)
      val expected = "abc_def"

      assert(result == expected)
    }

    "Upper camel case" in new Context {
      assert(underscore.upperCamelCase == upperCamel)

      val result = target.upperCamelCase(token)
      val expected = "AbcDef"

      assert(result == expected)
    }
  }

  class Context {
    // shared objects
    val simple = "hello world"
    val title = "Hello World"
    val lowerCamel = "helloWorld"
    val upperCamel = "HelloWorld"
    val underscore = "hello_world"

    val singular = "car"
    val plural = "cars"

    val token = ","
    val target = "abc,def"
  }
}
