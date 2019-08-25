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

import scala.collection.concurrent

/**
 * Simple facade interface to abstract over cache implementation.
 *
 * @tparam A the type of the keys used to store and retrieve values
 * @tparam B the type of the values stored in this cache
 */
trait Cache[A, B] {
  /**
   * If `key` is already in cache, returns its associated value.
   * Otherwise, computes value from `op`, stores it in cache, and returns it.
   *
   * @param key the key to test
   * @param op the computation yielding the value to associate with `key`, if previously unbound
   * @return the value previously associated with `key`, otherwise the result of evaluating `op`
   */
  def getOrElseUpdate(key: A, op: => B): B

  /**
   * The number of elements stored in this cache.
   *
   * @return the number of elements in cache
   */
  def size: Int
}

/**
 * A non-caching `Cache` implementation: `op` is always evaluated and returned.
 *
 * @tparam A the type of the keys used to store and retrieve values
 * @tparam B the type of the values stored in this cache
 */
class NoCache[A, B] extends Cache[A, B] {
  def getOrElseUpdate(key: A, op: => B): B = op

  def size: Int = 0
}

object NoCache {
  /**
   * Creates a new empty `NoCache`.
   *
   * @tparam A the type of the keys used to store and retrieve values
   * @tparam B the type of the values stored in this cache
   * @return the empty `NoCache`
   */
  def empty[A, B]: NoCache[A, B] = new NoCache[A, B]
}

/**
 * A simple, thread-safe `Cache` implementation based on trie maps.
 * Operations are atomic and allow concurrent access.
 * Once in the cache, values do not expire and are never released,
 * making this implementation not recommended when memory efficiency is of concern.
 *
 * @tparam A the type of the keys used to store and retrieve values
 * @tparam B the type of the values stored in this cache
 */
class SimpleCache[A, B] extends Cache[A, B] {
  private val cache = concurrent.TrieMap.empty[A, B]

  def getOrElseUpdate(key: A, op: => B): B = cache.getOrElseUpdate(key, op)

  def size: Int = cache.size
}

object SimpleCache {
  /**
   * Creates a new empty `SimpleCache`.
   *
   * @tparam A the type of the keys used to store and retrieve values
   * @tparam B the type of the values stored in this cache
   * @return the empyy `SimpleCache`
   */
  def empty[A, B]: SimpleCache[A, B] = new SimpleCache[A, B]
}
