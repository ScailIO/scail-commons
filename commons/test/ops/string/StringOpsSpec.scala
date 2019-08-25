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

class StringOpsSpec extends Spec {
  "StringOps should:" - {
    "Create new File instances" in {
      val parent = "/tmp"
      val child = "test.txt"

      val result = parent / child

      assert(result.getParent == parent)
      assert(result.getName == child)
    }

    "Parse booleans" in new Context {
      assert("true".asBoolean.contains(true))
      assert("false".asBoolean.contains(false))
      assert(abc.asBoolean.isEmpty)
    }

    "Parse bytes" in new Context {
      assert(one.asByte.contains((1: Byte)))
      assert(abc.asByte.isEmpty)
    }

    "Parse shorts" in new Context {
      assert(one.asShort.contains((1: Short)))
      assert(abc.asShort.isEmpty)
    }

    "Parse ints" in new Context {
      assert(one.asInt.contains(1))
      assert(abc.asInt.isEmpty)
    }

    "Parse longs" in new Context {
      assert(one.asLong.contains(1L))
      assert(abc.asLong.isEmpty)
    }

    "Parse floats" in new Context {
      assert(one.asFloat.contains(1.0f))
      assert(abc.asFloat.isEmpty)
    }

    "Parse doubles" in new Context {
      assert(one.asDouble.contains(1.0))
      assert(abc.asDouble.isEmpty)
    }

    "Convert strings to input streams" in new Context {
      val result = abc.asInputStream

      assert(result.read == 'a')
    }

    "Convert strings to readers" in new Context {
      val result = abc.reader

      assert(result.read == 'a')
    }

    "Compare strings in constant time" in new Context {
      assert(abc safeEquals abc)
    }

    "Check blank strings" in new Context {
      assert(blank.isBlank)
      assert(!abc.isBlank)

      assert(abc.nonBlank)
      assert(!blank.nonBlank)
    }

    "Check falsy strings" in new Context {
      assert("n".isFalsy)
      assert("no".isFalsy)
      assert(" f".isFalsy)
      assert("false ".isFalsy)
      assert(" off ".isFalsy)

      assert(!abc.isFalsy)
    }

    "Check truthy strings" in new Context {
      assert("y".isTruthy)
      assert("yes".isTruthy)
      assert(" t".isTruthy)
      assert("true ".isTruthy)
      assert(" on ".isTruthy)

      assert(!abc.isTruthy)
    }

    "Normalize space " in {
      val result = " abc \t def\n\nghi ".normalizeSpace
      val expected = "abc def ghi"

      assert(result == expected)
    }

    "Strip accents" in {
      val result = "àéîõüçÃÈÍÖÛÇ".stripAccents // scalastyle:ignore non.ascii.character.disallowed
      val expected = "aeioucAEIOUC"

      assert(result == expected)
    }

    "Convert to title case" in {
      val result = "the quick brown fox jumps over the lazy dog".titleCase
      val expected = "The Quick Brown Fox Jumps Over The Lazy Dog"

      assert(result == expected)
    }
  }

  class Context {
    // shared objects
    val abc = "abc"
    val one = "1"
    val blank = "\t \n"
  }
}
