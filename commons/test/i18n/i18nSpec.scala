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
package scail.commons.i18n

import scail.commons.Constants.Warts
import scail.commons.Spec

import java.time.ZoneId
import java.util.Date
import java.util.Locale

@SuppressWarnings(Array(Warts.Any, Warts.OldTime))
class i18nSpec extends Spec { // scalastyle:ignore class.name
  "i18n macro annotation should:" - {
    "support classes" in new Context {
      @i18n class Target
      val target = new Target

      val result = target.format.test(name = name, now = now)

      assert(target.test == expected.test)
      assert(result startsWith expected.format)
      assert(result.contains(localDate.getYear.toString))
      assert(result.contains(localDate.getMinute.toString))
    }

    "support objects" in new Context {
      @i18n object target

      val result = target.format.test(name = name, now = now)

      assert(target.test == expected.test)
      assert(result startsWith expected.format)
      assert(result.contains(localDate.getYear.toString))
      assert(result.contains(localDate.getMinute.toString))
    }

    "support classes with companion object" in new Context {
      @i18n class Target
      object Target { def pass: Boolean = true }

      val target = new Target

      val result = target.format.test(name = name, now = now)

      assert(target.test == expected.test)
      assert(result startsWith expected.format)
      assert(result.contains(localDate.getYear.toString))
      assert(result.contains(localDate.getMinute.toString))
      assert(Target.pass)
    }

    "support types extending Messages" in new Context {
      @i18n object target extends Messages(filename = "test")

      assert(target.noParameter == expected.test + ok)
      assert(target.number == number.toString)
      assert(target.stringParameter(string = name) == name + ok)
      assert(target.numberParameter(number = number) == number + ok)
      assert(target.dateParameter(date = now).contains(localDate.getYear.toString + ok))
      assert(target.`class` == "reserved keyword")

      val dateTimeParameterResult = target.dateTimeParameter(date = now)
      assert(dateTimeParameterResult.contains(localDate.getYear.toString + ok))
      assert(dateTimeParameterResult.contains(localDate.getMinute.toString))

      val dateStringParameterResult = target.dateStringParameter(date = now)
      assert(dateStringParameterResult.contains(localDate.getYear.toString + ok))
      assert(dateStringParameterResult.contains(localDate.getMinute.toString))

      val multiParameterResult = target.multiParameter(string = name, number = number, date = now)
      assert(multiParameterResult.startsWith(name + ok))
      assert(multiParameterResult.contains(number + ok))
      assert(multiParameterResult.endsWith(localDate.getYear.toString + ok))
    }

    "fail to compile invalid messages" in new Context {
      assertDoesNotCompile("""@i18n object target extends Messages(filename = "error")""")
    }
  }

  class Context {
    // shared objects
    implicit val defaultLocale: Locale = Locale.ENGLISH

    val name = "test"
    val now = new Date
    val number = 42

    val ok = "!"
    val localDate = now.toInstant.atZone(ZoneId.systemDefault)

    object expected {
      val test = "Hi"
      val format = "Hi, test! Today is "
    }
  }
}
