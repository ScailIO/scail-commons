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
package scail.commons.ops.util

import scail.commons.Spec

import com.typesafe.scalalogging.Logger

import org.slf4j.{Logger => Slf4j}

class LoggerOpsSpec extends Spec {
  "LoggerOps should:" - {
    "Log exceptions" in new Context {
      logger.error(exception)
      underlying.error(any[String], any[Throwable]) was called

      logger.warn(exception)
      underlying.warn(any[String], any[Throwable]) was called

      logger.info(exception)
      underlying.info(any[String], any[Throwable]) was called

      logger.debug(exception)
      underlying.debug(any[String], any[Throwable]) was called

      logger.trace(exception)
      underlying.trace(any[String], any[Throwable]) was called
    }
  }

  class Context {
    // shared objects
    val exception = new Exception

    // shared mocks
    val underlying = mock[Slf4j]

    val logger = Logger(underlying)

    // common expectations
    underlying.isErrorEnabled returns true
    underlying.isWarnEnabled returns true
    underlying.isInfoEnabled returns true
    underlying.isDebugEnabled returns true
    underlying.isTraceEnabled returns true
  }
}
