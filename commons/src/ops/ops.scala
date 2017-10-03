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
package scail.commons

import scail.commons.Constants.Goats
import scail.commons.Constants.Warts

import scala.concurrent.Future

package object ops {
  /**
   * Extension methods for `Any`.
   */
  implicit class AnyOps[A](private val value: A) extends AnyVal {
    /**
     * Creates an already completed `Future` containing `value`.
     *
     * @return the already completed `Future` with the specified result
     */
    @inline
    def future: Future[A] = Future.successful(value)

    /**
     * Creates an `Option` containing `value`.
     *
     * @return `Some(value)` if `value` is not `null`, `None` otherwise
     */
    @inline
    def option: Option[A] = Option(value)

    /**
     * Applies side-effectul method to `value`, returning `value`.
     *
     * @param f the side-effectful method to apply to `value`
     * @return `value`
     */
    def tap(f: A => Unit): A = {
      f(value)
      value
    }
  }

  /**
   * Extension methods for `AnyRef`.
   */
  implicit class AnyRefOps[A <: AnyRef](private val value: A) extends AnyVal {
    /**
     * Whether a reference is null.
     *
     * @return `true` if null, `false` otherwise
     */
    @inline
    def isNull: Boolean = !nonNull

    /**
     * Whether a reference is non-null.
     *
     * @return `true` if non-null, `false` otherwise
     */
    @inline
    @SuppressWarnings(Array(Goats.NullParameter, Warts.Null))
    def nonNull: Boolean = value != (null: AnyRef) // scalastyle:ignore null
  }
}
