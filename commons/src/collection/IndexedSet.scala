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
package scail.commons.collection

import scala.collection.AbstractSet
import scala.collection.SetLike
import scala.collection.breakOut

/**
 * This class implements immutable indexed sets using a hash trie.
 *
 * An indexed set is like a set, however elements are indexed (hashed) by a given key.
 * A function `indexedBy: A => B` is used to map elements to their keys.
 * The key can be used to test for membership or retrieve the element, like in a map.
 *
 * Indexed sets guarantee no two elements share the same key.
 *
 * @tparam A the type of the elements contained in this indexed set
 * @tparam B the type of the keys in this indexed set
 */
final class IndexedSet[A, B] private (map: Map[B, A], indexedBy: A => B)
  extends AbstractSet[A]
  with SetLike[A, IndexedSet[A, B]] {
  def contains(elem: A): Boolean = map.get(indexedBy(elem)).contains(elem)
  def iterator: Iterator[A] = map.valuesIterator
  // scalastyle:off method.name ensure.single.space.after.token
  def +(elem: A): IndexedSet[A, B] = new IndexedSet(map + (indexedBy(elem) -> elem), indexedBy)
  def -(elem: A): IndexedSet[A, B] = new IndexedSet(map - indexedBy(elem), indexedBy)
  // scalastyle:on

  override def empty: IndexedSet[A, B] = IndexedSet.empty(indexedBy)
  override def foreach[U](f: A => U): Unit = map foreach { case (_, v) => f(v) }
  override def size: Int = map.size

  /**
   * Tests if some key is contained in this set.
   *
   * @param key the key to test for membership
   * @return `true` if `key` is contained in this set, `false` otherwise
   */
  def containsKey(key: B): Boolean = map.contains(key)

  /**
   * Optionally returns the element associated with a key.
   *
   * @param key the key
   * @return the option value containing the element associated with `key` in this set,
   *         or `None` if none exists
   */
  def get(key: B): Option[A] = map.get(key)
}

/**
 * This object provides a set of operations needed to create `IndexedSet` values.
 */
object IndexedSet {
  /**
   * Creates a collection with the specified elements.
   *
   * @param indexedBy a function mapping an element to its key
   * @param elems the elements of the created collection
   * @tparam A the type of the elements contained in this indexed set
   * @tparam B the type of the keys in this indexed set
   * @return the new collection with elements elems
   */
  def apply[A, B](indexedBy: A => B)(elems: A*): IndexedSet[A, B] = {
    val map: Map[B, A] = elems.map { e => (indexedBy(e), e) } (breakOut)
    new IndexedSet(map, indexedBy)
  }

  /**
   * An empty collection of type Set[A].
   *
   * @param indexedBy a function mapping an element to its key
   * @tparam A the type of the elements contained in this indexed set
   * @tparam B the type of the keys in this indexed set
   * @return the empty `IndexedSet`
   */
  def empty[A, B](indexedBy: A => B): IndexedSet[A, B] = new IndexedSet(Map.empty[B, A], indexedBy)
}
