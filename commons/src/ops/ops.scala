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

  /**
   * Extension methods for `Boolean`.
   */
  implicit class BooleanOps(private val value: Boolean) extends AnyVal {
    /**
     * Ternary operator.
     *
     * @example {{{
     * cond ? "yes" | "no"
     * }}}
     *
     * @param `true` the value to be returned if the boolean expression is true
     * @tparam A the return type of the expression
     * @return the first parameter if the boolean value is true, the second parameter otherwise
     */
    // scalastyle:off method.name method.argument.name
    def ?[A](`true`: => A): Conditional[A] = new Conditional(value, `true`)
    // scalastyle:on

    /**
     * Converts a boolean value to integer.
     *
     * @return `1` if the boolean is true, `0` otherwise
     */
    @inline
    def asInt: Int = if (value) 1 else 0

    /**
     * Optionally returns a value if the boolean expression is true.
     *
     * @param a the value to be returned as an `Option`
     * @tparam A the type of value `a`
     * @return `Option(a)` if the boolean expression is true, `None` otherwise
     */
    def thenOption[A](a: => A): Option[A] = if (value) Option(a) else None
  }

  /**
   * Extension methods for `Byte`.
   */
  implicit class ByteOps(private val value: Byte) extends AnyVal {
    /**
     * Whether `value` is even.
     *
     * @return `true` if `value` is even, `false` otherwise
     */
    @inline
    def isEven: Boolean = (value & 1) == 0

    /**
     * Whether `value` is odd.
     *
     * @return `true` if `value` is odd, `false` otherwise
     */
    @inline
    def isOdd: Boolean = (value & 1) != 0
  }

  /**
   * Extension methods for `Char`.
   */
  implicit class CharOps(private val value: Char) extends AnyVal {
    /**
     * Whether `value` is even.
     *
     * @return `true` if `value` is even, `false` otherwise
     */
    @inline
    def isEven: Boolean = (value & 1) == 0

    /**
     * Whether `value` is odd.
     *
     * @return `true` if `value` is odd, `false` otherwise
     */
    @inline
    def isOdd: Boolean = (value & 1) != 0
  }
}

package ops {
  final class Conditional[A](value: Boolean, `true`: => A) {
    /**
     * Ternary operator.
     *
     * @example {{{
     * cond ? "yes" | "no"
     * }}}
     *
     * @param `false` the value to be returned if the boolean expression is false
     * @return the first parameter if the boolean value is true, the second parameter otherwise
     */
    // scalastyle:off method.name  method.argument.name
    def |(`false`: => A): A = if (value) `true` else `false`
    // scalastyle:on
  }
}
