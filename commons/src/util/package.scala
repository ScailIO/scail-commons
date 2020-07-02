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
package scail.commons

import scala.util.control.NonFatal

package object util {
  /**
   * Exception handler that returns a default value if a non-fatal exception is thrown.
   *
   * @example {{{
   * val result = defaultsTo("") {
   *   "abc".substring(4)
   * }
   * }}}
   *
   * @param default the default value to return when a non-fatal exception is caught
   * @param exp the expression to be evaluated
   * @tparam A the type of the default return value
   * @return the evaluation of `exp` if no exception is thrown,
   *         `default` if a non-fatal exception was thrown
   */
  def defaultsTo[A](default: => A)(exp: => A): A = {
    try exp catch {
      case NonFatal(_) => default
    }
  }
}
