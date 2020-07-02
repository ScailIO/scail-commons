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
package scail.commons.i18n

import scail.commons.Constants.Warts
import scail.commons.Spec

import com.typesafe.scalalogging.Logger

import org.slf4j.{Logger => Slf4j}

import java.time.ZoneId
import java.util.Date
import java.util.Locale

@SuppressWarnings(Array(Warts.OldTime))
class MessagesSpec extends Spec {
  "Messages" - {
    ".apply should:" - {
      "return localized messages for different locales" in new Context {
        val key = 'test

        assert(messages(key)(en) == expected.en)
        assert(messages(key)(enCa) == expected.enCa)
        assert(messages(key)(ptBr) == expected.ptBr)

        logger wasNever called
      }

      "return default message for non-existing messages" in new Context {
        val key = "noTest"

        val noEn = messages(key)(en)
        assert(noEn.contains(key))
        assert(noEn.contains(en.toLanguageTag))
        logger.warn(any[String], any[String]) was called

        val noEnCa = messages(key)(enCa)
        assert(noEnCa.contains(key))
        assert(noEnCa.contains(enCa.toLanguageTag))
        logger.warn(any[String], any[String]) wasCalled twice

        val noPtBr = messages(key)(ptBr)
        assert(noPtBr.contains(key))
        assert(noPtBr.contains(ptBr.toLanguageTag))
        logger.warn(any[String], any[String]) wasCalled threeTimes
      }

      "format localized messages for different locales" in new Context {
        val key = "format.test"

        val fmtEn = messages(key, name, now)(en)
        assert(fmtEn startsWith expected.en)
        assert(fmtEn.contains(localDate.getYear.toString))
        assert(fmtEn.contains(localDate.getMinute.toString))

        val fmtEnCa = messages(key, name, now)(enCa)
        assert(fmtEnCa startsWith expected.enCa)
        assert(fmtEnCa.contains(localDate.getYear.toString))
        assert(fmtEnCa.contains(localDate.getMinute.toString))

        val fmtPtBr = messages(key, name, now)(ptBr)
        assert(fmtPtBr startsWith expected.ptBr)
        assert(fmtPtBr.contains(localDate.getYear.toString))
        assert(fmtPtBr.contains(localDate.getMinute.toString))

        logger wasNever called
      }

      "return default message for non-existing formatted messages" in new Context {
        val key = "noFormatTest"

        val noEn = messages(key, name, now)(en)
        assert(noEn.contains(key))
        assert(noEn.contains(en.toLanguageTag))
        logger.warn(any[String], any[String]) was called

        val noEnCa = messages(key, name, now)(enCa)
        assert(noEnCa.contains(key))
        assert(noEnCa.contains(enCa.toLanguageTag))
        logger.warn(any[String], any[String]) wasCalled twice

        val noPtBr = messages(key, name, now)(ptBr)
        assert(noPtBr.contains(key))
        assert(noPtBr.contains(ptBr.toLanguageTag))
        logger.warn(any[String], any[String]) wasCalled threeTimes
      }
    }

    ".get should:" - {
      "return localized messages for different locales" in new Context {
        val key = 'test

        assert(messages.get(key)(en).contains(expected.en))
        assert(messages.get(key)(enCa).contains(expected.enCa))
        assert(messages.get(key)(ptBr).contains(expected.ptBr))

        logger wasNever called
      }

      "return None for non-existing messages" in new Context {
        val key = 'noTest

        assert(messages.get(key)(en).isEmpty)
        logger.warn(any[String], any[String]) was called

        assert(messages.get(key)(enCa).isEmpty)
        logger.warn(any[String], any[String]) wasCalled twice

        assert(messages.get(key)(ptBr).isEmpty)
        logger.warn(any[String], any[String]) wasCalled threeTimes
      }

      "format localized messages for different locales" in new Context {
        val key = "format.test"

        val fmtEn = messages.get(key, name, now)(en)
        assert(fmtEn exists (_ startsWith expected.en))
        assert(fmtEn exists (_.contains(localDate.getYear.toString)))
        assert(fmtEn exists (_.contains(localDate.getMinute.toString)))

        val fmtEnCa = messages.get(key, name, now)(enCa)
        assert(fmtEnCa exists (_ startsWith expected.enCa))
        assert(fmtEnCa exists (_.contains(localDate.getYear.toString)))
        assert(fmtEnCa exists (_.contains(localDate.getMinute.toString)))

        val fmtPtBr = messages.get(key, name, now)(ptBr)
        assert(fmtPtBr exists (_ startsWith expected.ptBr))
        assert(fmtPtBr exists (_.contains(localDate.getYear.toString)))
        assert(fmtPtBr exists (_.contains(localDate.getMinute.toString)))

        logger wasNever called
      }

      "return None for non-existing formatted messages" in new Context {
        val key = 'noFormatTest

        assert(messages.get(key, name, now)(en).isEmpty)
        logger.warn(any[String], any[String]) was called

        assert(messages.get(key, name, now)(enCa).isEmpty)
        logger.warn(any[String], any[String]) wasCalled twice

        assert(messages.get(key, name, now)(ptBr).isEmpty)
        logger.warn(any[String], any[String]) wasCalled threeTimes
      }
    }
  }

  @SuppressWarnings(Array(Warts.ExposedTuples))
  class Context { context =>
    // shared objects
    val messages = new Messages {
      override lazy val logger = Logger(context.logger)
    }

    val en = Locale.ENGLISH
    val enCa = Locale.CANADA
    val ptBr = Locale.forLanguageTag("pt-BR")

    val name = "name" -> "Scail"

    val date = new Date
    val now = "now" -> date
    val localDate = date.toInstant.atZone(ZoneId.systemDefault)

    object expected {
      val en = "Hi"
      val enCa = "Hello"
      val ptBr = "Oi"
    }

    // shared mocks
    val logger = mock[Slf4j]

    // common expectations
    logger.isWarnEnabled returns true
  }
}
