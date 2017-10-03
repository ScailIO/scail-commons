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

import scail.commons.Constants.Warts

import scala.collection.IndexedSeq
import scala.collection.Map
import scala.collection.MapLike
import scala.collection.TraversableLike
import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds
import scala.reflect.ClassTag
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

  /**
   * Extension methods for `Map[A, B]`.
   */
  implicit class MapOps[A, B, M[A, B] <: MapLike[A, B, M[A, B]] with Map[A, B]](
    private val value: M[A, B]
  ) extends AnyVal {
    /**
     * Whether the map contains a given key value pair as an element.
     *
     * @param k the key
     * @param v the value
     * @return `true` if the map contains the pair key value, `false` otherwise
     */
    @inline
    def containsEntry(k: A, v: B): Boolean = value.get(k).contains(v)

    /**
     * Whether the map contains the given entry.
     *
     * @param entry a tuple representing the key value pair
     * @return `true` if the map contains the entry, `false` otherwise
     */
    @inline
    @SuppressWarnings(Array(Warts.ExposedTuples))
    def containsEntry(entry: (A, B)): Boolean = containsEntry(entry._1, entry._2)

    /**
     * Whether a predicate holds for at least one key of the map.
     *
     * @param p the predicate used to test keys
     * @return `true` if `p` holds for some of the keys of the map, `false` otherwise
     */
    @inline
    def existsKey(p: A => Boolean): Boolean = value.exists(e => p(e._1))

    /**
     * Whether a predicate holds for at least one value of the map.
     *
     * @param p the predicate used to test values
     * @return `true` if `p` holds for some of the values of the map, `false` otherwise
     */
    @inline
    def existsValue(p: B => Boolean): Boolean = value.exists(e => p(e._2))

    /**
     * Filters the map by retaining only values satisfying a predicate.
     *
     * @param p the predicate used to test values
     * @return the map with only the key value pairs whose value satisfies the predicate `p`
     */
    def filterValues(p: B => Boolean): M[A, B] = value filter { case (_, v) => p(v) }

    /**
     * Whether a predicate holds for all keys of the map.
     *
     * @param p the predicate used to test keys
     * @return `true` if `p` holds for all keys of the map, `false` otherwise
     */
    @inline
    def forallKey(p: A => Boolean): Boolean = value.forall(e => p(e._1))

    /**
     * Whether a predicate holds for all values of the map.
     *
     * @param p the predicate used to test values
     * @return `true` if `p` holds for all values of the map, `false` otherwise
     */
    @inline
    def forallValue(p: B => Boolean): Boolean = value.forall(e => p(e._2))

    /**
     * Transforms the map by applying a function to every key.
     *
     * @param f the function used to transform keys of this map
     * @tparam C the key type of the returned map
     * @return the new map resulting from applying the given function `f`
     *         to each key and collecting the results
     *
     * @usecase def mapKeys[C](f: A => C): M[C, B]
     */
    @SuppressWarnings(Array(Warts.ExposedTuples))
    def mapKeys[C](f: A => C)(implicit bf: CanBuildFrom[M[A, B], (C, B), M[C, B]]): M[C, B] = {
      value map { case (k, v) => (f(k), v) }
    }
  }

  /**
   * Extension methods for `Traversable[A]`.
   */
  implicit class TraversableOps[A, T[A] <: TraversableLike[A, T[A]]](
    private val value: T[A]
  ) extends AnyVal {
    /**
     * Finds duplicated elements in a collection.
     *
     * @return the list of duplicated elements
     */
    @inline
    def duplicates: Seq[A] = duplicatesBy(identity)

    /**
     * Finds duplicated elements in a collection according to a transformation function.
     *
     * @param f the transformation function mapping elements to some other domain
     * @return the list of duplicated elements according to `f`
     */
    def duplicatesBy[B](f: A => B): Seq[B] = {
      value.groupBy(f).collect {
        case (e, c) if c.size > 1 => e
      }.to[Seq]
    }

    /**
     * Filters the elements of a collection by their type
     *
     * @tparam B the type of the elements to be filtered
     * @return the new collection with only the elements of type `B`
     *
     * @usecase def filterByType[B <: A]: T[B]
     */
    @inline
    def filterByType[B <: A: ClassTag](implicit bf: CanBuildFrom[T[A], B, T[B]]): T[B] = {
      value collect {
        case e: B => e
      }
    }
  }
}
