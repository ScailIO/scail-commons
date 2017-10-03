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

import scala.collection.IndexedSeq
import scala.util.Random

package object collection {
  /**
   * Extension methods for `IndexedSeq[A]`.
   */
  implicit class IndexedSeqOps[A](private val value: IndexedSeq[A]) extends AnyVal {
    /**
     * Returns a random element from the collection
     *
     * @return the random element
     */
    @inline
    def randomElement: A = value(Random.nextInt(value.length))
  }
}
