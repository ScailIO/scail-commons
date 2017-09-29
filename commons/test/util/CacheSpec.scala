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
package scail.commons.util

import scail.commons.Spec

class CacheSpec extends Spec {
  "NoCache should:" - {
    "return the same value" in new Context {
      val noCache = NoCache.empty[String, String]

      val result = noCache.getOrElseUpdate(key, value)

      assert(result == value)
    }
  }

  "SimpleCache should:" - {
    "store and return the same value" in new Context {
      val simpleCache = SimpleCache.empty[String, String]
      assert(simpleCache.size == 0)

      val result = simpleCache.getOrElseUpdate(key, value)
      assert(result == value)
      assert(simpleCache.size == 1)
    }

    "not evaluate an already stored value" in new Context {
      val simpleCache = SimpleCache.empty[String, String]

      simpleCache.getOrElseUpdate(key, value)
      simpleCache.getOrElseUpdate(key, fail)

      assert(simpleCache.size == 1)
    }
  }

  class Context {
    // shared objects
    val key = "aKey"
    val value = "aValue"
  }
}
