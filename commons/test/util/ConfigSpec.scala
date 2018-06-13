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

import scail.commons.Spec
import scail.commons.util.Config.ConfigOps

import com.typesafe.config.ConfigException

import scala.concurrent.duration.{Duration => sDuration}
import scala.concurrent.duration.FiniteDuration
import scala.math.BigDecimal
import scala.math.BigInt

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.MonthDay
import java.time.OffsetDateTime
import java.time.Period
import java.time.Year
import java.time.YearMonth
import java.time.ZonedDateTime
import java.time.ZoneId
import java.util.Locale
import java.util.UUID

class ConfigSpec extends Spec {
  "Config" - {
    ".required should:" - {
      "retrieve native values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBoolean = 'aBoolean.required[Boolean]
          val aDouble = 'aDouble.required[Double]
          val aDuration = 'aDuration.required[Duration]
          val aInt = 'aInt.required[Int]
          val aLong = 'aLong.required[Long]
          val aString = 'aString.required[String]
        }

        assert(MyConfig.aBoolean == Configuration.aBoolean)
        assert(MyConfig.aDouble == Configuration.aDouble)
        assert(MyConfig.aDuration == Configuration.aDuration)
        assert(MyConfig.aInt == Configuration.aInt)
        assert(MyConfig.aLong == Configuration.aLong)
        assert(MyConfig.aString == Configuration.aString)
      }

      "retrieve mapped values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBigDecimal = 'aBigDecimal.required[BigDecimal]
          val aBigInt = 'aBigInt.required[BigInt]
          val aLocalDate = 'aLocalDate.required[LocalDate]
          val aLocalDateTime = 'aLocalDateTime.required[LocalDateTime]
          val aLocale = 'aLocale.required[Locale]
          val aLocalTime = 'aLocalTime.required[LocalTime]
          val aMonthDay = 'aMonthDay.required[MonthDay]
          val aOffsetDateTime = 'aOffsetDateTime.required[OffsetDateTime]
          val aPeriod = 'aPeriod.required[Period]
          val aUuid = 'aUuid.required[UUID]
          val aYear = 'aYear.required[Year]
          val aYearMonth = 'aYearMonth.required[YearMonth]
          val aZonedDateTime = 'aZonedDateTime.required[ZonedDateTime]
          val aZoneId = 'aZoneId.required[ZoneId]
          val aFiniteDuration = 'aFiniteDuration.required[FiniteDuration]
        }

        assert(MyConfig.aBigDecimal == Configuration.aBigDecimal)
        assert(MyConfig.aBigInt == Configuration.aBigInt)
        assert(MyConfig.aLocalDate == Configuration.aLocalDate)
        assert(MyConfig.aLocalDateTime == Configuration.aLocalDateTime)
        assert(MyConfig.aLocale == Configuration.aLocale)
        assert(MyConfig.aLocalTime == Configuration.aLocalTime)
        assert(MyConfig.aMonthDay == Configuration.aMonthDay)
        assert(MyConfig.aOffsetDateTime == Configuration.aOffsetDateTime)
        assert(MyConfig.aPeriod == Configuration.aPeriod)
        assert(MyConfig.aUuid == Configuration.aUuid)
        assert(MyConfig.aYear == Configuration.aYear)
        assert(MyConfig.aYearMonth == Configuration.aYearMonth)
        assert(MyConfig.aZonedDateTime == Configuration.aZonedDateTime)
        assert(MyConfig.aZoneId == Configuration.aZoneId)
        assert(MyConfig.aFiniteDuration == Configuration.aFiniteDuration)
      }

      "retrieve native list values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBooleanList = 'aBooleanList.required[Seq[Boolean]]
          val aDoubleList = 'aDoubleList.required[Seq[Double]]
          val aDurationList = 'aDurationList.required[Seq[Duration]]
          val aIntList = 'aIntList.required[Seq[Int]]
          val aLongList = 'aLongList.required[Seq[Long]]
          val aStringList = 'aStringList.required[Seq[String]]
        }

        assert(MyConfig.aBooleanList == Configuration.aBooleanList)
        assert(MyConfig.aDoubleList == Configuration.aDoubleList)
        assert(MyConfig.aDurationList == Configuration.aDurationList)
        assert(MyConfig.aIntList == Configuration.aIntList)
        assert(MyConfig.aLongList == Configuration.aLongList)
        assert(MyConfig.aStringList == Configuration.aStringList)
      }

      "retrieve mapped list values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBigDecimalList = 'aBigDecimalList.required[Seq[BigDecimal]]
          val aBigIntList = 'aBigIntList.required[Seq[BigInt]]
          val aLocalDateList = 'aLocalDateList.required[Seq[LocalDate]]
          val aLocalDateTimeList = 'aLocalDateTimeList.required[Seq[LocalDateTime]]
          val aLocaleList = 'aLocaleList.required[Seq[Locale]]
          val aLocalTimeList = 'aLocalTimeList.required[Seq[LocalTime]]
          val aMonthDayList = 'aMonthDayList.required[Seq[MonthDay]]
          val aOffsetDateTimeList = 'aOffsetDateTimeList.required[Seq[OffsetDateTime]]
          val aPeriodList = 'aPeriodList.required[Seq[Period]]
          val aUuidList = 'aUuidList.required[Seq[UUID]]
          val aYearList = 'aYearList.required[Seq[Year]]
          val aYearMonthList = 'aYearMonthList.required[Seq[YearMonth]]
          val aZonedDateTimeList = 'aZonedDateTimeList.required[Seq[ZonedDateTime]]
          val aZoneIdList = 'aZoneIdList.required[Seq[ZoneId]]
          val aFiniteDurationList = 'aFiniteDurationList.required[Seq[FiniteDuration]]
        }

        assert(MyConfig.aBigDecimalList == Configuration.aBigDecimalList)
        assert(MyConfig.aBigIntList == Configuration.aBigIntList)
        assert(MyConfig.aLocalDateList == Configuration.aLocalDateList)
        assert(MyConfig.aLocalDateTimeList == Configuration.aLocalDateTimeList)
        assert(MyConfig.aLocaleList == Configuration.aLocaleList)
        assert(MyConfig.aLocalTimeList == Configuration.aLocalTimeList)
        assert(MyConfig.aMonthDayList == Configuration.aMonthDayList)
        assert(MyConfig.aOffsetDateTimeList == Configuration.aOffsetDateTimeList)
        assert(MyConfig.aPeriodList == Configuration.aPeriodList)
        assert(MyConfig.aUuidList == Configuration.aUuidList)
        assert(MyConfig.aYearList == Configuration.aYearList)
        assert(MyConfig.aYearMonthList == Configuration.aYearMonthList)
        assert(MyConfig.aZonedDateTimeList == Configuration.aZonedDateTimeList)
        assert(MyConfig.aZoneIdList == Configuration.aZoneIdList)
        assert(MyConfig.aFiniteDurationList == Configuration.aFiniteDurationList)
      }

      "retrieve native and mapped values without type parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val aBoolean: Boolean = 'aBoolean.required
          val aDouble: Double = 'aDouble.required
          val aDuration: Duration = 'aDuration.required
          val aInt: Int = 'aInt.required
          val aLong: Long = 'aLong.required
          val aString: String = 'aString.required

          // mapped values
          val aBigDecimal: BigDecimal = 'aBigDecimal.required
          val aBigInt: BigInt = 'aBigInt.required
          val aLocalDate: LocalDate = 'aLocalDate.required
          val aLocalDateTime: LocalDateTime = 'aLocalDateTime.required
          val aLocale: Locale = 'aLocale.required
          val aLocalTime: LocalTime = 'aLocalTime.required
          val aMonthDay: MonthDay = 'aMonthDay.required
          val aOffsetDateTime: OffsetDateTime = 'aOffsetDateTime.required
          val aPeriod: Period = 'aPeriod.required
          val aUuid: UUID = 'aUuid.required
          val aYear: Year = 'aYear.required
          val aYearMonth: YearMonth = 'aYearMonth.required
          val aZonedDateTime: ZonedDateTime = 'aZonedDateTime.required
          val aZoneId: ZoneId = 'aZoneId.required
          val aFiniteDuration: FiniteDuration = 'aFiniteDuration.required
        }

        // native values
        assert(MyConfig.aBoolean == Configuration.aBoolean)
        assert(MyConfig.aDouble == Configuration.aDouble)
        assert(MyConfig.aDuration == Configuration.aDuration)
        assert(MyConfig.aInt == Configuration.aInt)
        assert(MyConfig.aLong == Configuration.aLong)
        assert(MyConfig.aString == Configuration.aString)

        // mapped values
        assert(MyConfig.aBigDecimal == Configuration.aBigDecimal)
        assert(MyConfig.aBigInt == Configuration.aBigInt)
        assert(MyConfig.aLocalDate == Configuration.aLocalDate)
        assert(MyConfig.aLocalDateTime == Configuration.aLocalDateTime)
        assert(MyConfig.aLocale == Configuration.aLocale)
        assert(MyConfig.aLocalTime == Configuration.aLocalTime)
        assert(MyConfig.aMonthDay == Configuration.aMonthDay)
        assert(MyConfig.aOffsetDateTime == Configuration.aOffsetDateTime)
        assert(MyConfig.aPeriod == Configuration.aPeriod)
        assert(MyConfig.aUuid == Configuration.aUuid)
        assert(MyConfig.aYear == Configuration.aYear)
        assert(MyConfig.aYearMonth == Configuration.aYearMonth)
        assert(MyConfig.aZonedDateTime == Configuration.aZonedDateTime)
        assert(MyConfig.aZoneId == Configuration.aZoneId)
        assert(MyConfig.aFiniteDuration == Configuration.aFiniteDuration)
      }

      "retrieve native and mapped list values without type parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native list values
          val aBooleanList: Seq[Boolean] = 'aBooleanList.required
          val aDoubleList: Seq[Double] = 'aDoubleList.required
          val aDurationList: Seq[Duration] = 'aDurationList.required
          val aIntList: Seq[Int] = 'aIntList.required
          val aLongList: Seq[Long] = 'aLongList.required
          val aStringList: Seq[String] = 'aStringList.required

          // mapped list values
          val aBigDecimalList: Seq[BigDecimal] = 'aBigDecimalList.required
          val aBigIntList: Seq[BigInt] = 'aBigIntList.required
          val aLocalDateList: Seq[LocalDate] = 'aLocalDateList.required
          val aLocalDateTimeList: Seq[LocalDateTime] = 'aLocalDateTimeList.required
          val aLocaleList: Seq[Locale] = 'aLocaleList.required
          val aLocalTimeList: Seq[LocalTime] = 'aLocalTimeList.required
          val aMonthDayList: Seq[MonthDay] = 'aMonthDayList.required
          val aOffsetDateTimeList: Seq[OffsetDateTime] = 'aOffsetDateTimeList.required
          val aPeriodList: Seq[Period] = 'aPeriodList.required
          val aUuidList: Seq[UUID] = 'aUuidList.required
          val aYearList: Seq[Year] = 'aYearList.required
          val aYearMonthList: Seq[YearMonth] = 'aYearMonthList.required
          val aZonedDateTimeList: Seq[ZonedDateTime] = 'aZonedDateTimeList.required
          val aZoneIdList: Seq[ZoneId] = 'aZoneIdList.required
          val aFiniteDurationList: Seq[FiniteDuration] = 'aFiniteDurationList.required
        }

        // native list values
        assert(MyConfig.aBooleanList == Configuration.aBooleanList)
        assert(MyConfig.aDoubleList == Configuration.aDoubleList)
        assert(MyConfig.aDurationList == Configuration.aDurationList)
        assert(MyConfig.aIntList == Configuration.aIntList)
        assert(MyConfig.aLongList == Configuration.aLongList)
        assert(MyConfig.aStringList == Configuration.aStringList)

        // mapped list values
        assert(MyConfig.aBigDecimalList == Configuration.aBigDecimalList)
        assert(MyConfig.aBigIntList == Configuration.aBigIntList)
        assert(MyConfig.aLocalDateList == Configuration.aLocalDateList)
        assert(MyConfig.aLocalDateTimeList == Configuration.aLocalDateTimeList)
        assert(MyConfig.aLocaleList == Configuration.aLocaleList)
        assert(MyConfig.aLocalTimeList == Configuration.aLocalTimeList)
        assert(MyConfig.aMonthDayList == Configuration.aMonthDayList)
        assert(MyConfig.aOffsetDateTimeList == Configuration.aOffsetDateTimeList)
        assert(MyConfig.aPeriodList == Configuration.aPeriodList)
        assert(MyConfig.aUuidList == Configuration.aUuidList)
        assert(MyConfig.aYearList == Configuration.aYearList)
        assert(MyConfig.aYearMonthList == Configuration.aYearMonthList)
        assert(MyConfig.aZonedDateTimeList == Configuration.aZonedDateTimeList)
        assert(MyConfig.aZoneIdList == Configuration.aZoneIdList)
        assert(MyConfig.aFiniteDurationList == Configuration.aFiniteDurationList)
      }

      "throw exception for non-existing keys: native and native list values" in new Context {
        val configMissing = mock[ConfigException.Missing]

        // native values
        typesafeConfig.getBoolean("noBoolean") shouldThrow configMissing
        typesafeConfig.getDouble("noDouble") shouldThrow configMissing
        typesafeConfig.getDuration("noDuration") shouldThrow configMissing
        typesafeConfig.getInt("noInt") shouldThrow configMissing
        typesafeConfig.getLong("noLong") shouldThrow configMissing
        typesafeConfig.getString("noString") shouldThrow configMissing

        // native list values
        typesafeConfig.getBooleanList("noBooleanList") shouldThrow configMissing
        typesafeConfig.getDoubleList("noDoubleList") shouldThrow configMissing
        typesafeConfig.getDurationList("noDurationList") shouldThrow configMissing
        typesafeConfig.getIntList("noIntList") shouldThrow configMissing
        typesafeConfig.getLongList("noLongList") shouldThrow configMissing
        typesafeConfig.getStringList("noStringList") shouldThrow configMissing

        object MyConfig extends Config(typesafeConfig) {
          // native values
          lazy val noBoolean = 'noBoolean.required[Boolean]
          lazy val noDouble = 'noDouble.required[Double]
          lazy val noDuration = 'noDuration.required[Duration]
          lazy val noInt = 'noInt.required[Int]
          lazy val noLong = 'noLong.required[Long]
          lazy val noString = 'noString.required[String]

          // native list values
          lazy val noBooleanList = 'noBooleanList.required[Seq[Boolean]]
          lazy val noDoubleList = 'noDoubleList.required[Seq[Double]]
          lazy val noDurationList = 'noDurationList.required[Seq[Duration]]
          lazy val noIntList = 'noIntList.required[Seq[Int]]
          lazy val noLongList = 'noLongList.required[Seq[Long]]
          lazy val noStringList = 'noStringList.required[Seq[String]]
        }

        // native values
        assertThrows[ConfigException.Missing](MyConfig.noBoolean)
        assertThrows[ConfigException.Missing](MyConfig.noDouble)
        assertThrows[ConfigException.Missing](MyConfig.noDuration)
        assertThrows[ConfigException.Missing](MyConfig.noInt)
        assertThrows[ConfigException.Missing](MyConfig.noLong)
        assertThrows[ConfigException.Missing](MyConfig.noString)

        // native list values
        assertThrows[ConfigException.Missing](MyConfig.noBooleanList)
        assertThrows[ConfigException.Missing](MyConfig.noDoubleList)
        assertThrows[ConfigException.Missing](MyConfig.noDurationList)
        assertThrows[ConfigException.Missing](MyConfig.noIntList)
        assertThrows[ConfigException.Missing](MyConfig.noLongList)
        assertThrows[ConfigException.Missing](MyConfig.noStringList)
      }

      "throw exception for non-existing keys: mapped and mapped list values" in new Context {
        val configMissing = mock[ConfigException.Missing]

        // native values
        typesafeConfig.getString(any[String]) shouldThrow configMissing
        typesafeConfig.getDuration("noFiniteDuration") shouldThrow configMissing

        // native list values
        typesafeConfig.getStringList(any[String]) shouldThrow configMissing
        typesafeConfig.getDurationList("noFiniteDurationList") shouldThrow configMissing

        object MyConfig extends Config(typesafeConfig) {
          // mapped values
          lazy val noBigDecimal = 'noBigDecimal.required[BigDecimal]
          lazy val noBigInt = 'noBigInt.required[BigInt]
          lazy val noLocalDate = 'noLocalDate.required[LocalDate]
          lazy val noLocalDateTime = 'noLocalDateTime.required[LocalDateTime]
          lazy val noLocale = 'noLocalTime.required[Locale]
          lazy val noLocalTime = 'noLocalTime.required[LocalTime]
          lazy val noMonthDay = 'noMonthDay.required[MonthDay]
          lazy val noOffsetDateTime = 'noOffsetDateTime.required[OffsetDateTime]
          lazy val noPeriod = 'noPeriod.required[Period]
          lazy val noUuid = 'noUuid.required[UUID]
          lazy val noYear = 'noYear.required[Year]
          lazy val noYearMonth = 'noYearMonth.required[YearMonth]
          lazy val noZonedDateTime = 'noZonedDateTime.required[ZonedDateTime]
          lazy val noZoneId = 'noZoneId.required[ZoneId]
          lazy val noFiniteDuration = 'noFiniteDuration.required[FiniteDuration]

          // mapped list values
          lazy val noBigDecimalList = 'noBigDecimalList.required[Seq[BigDecimal]]
          lazy val noBigIntList = 'noBigIntList.required[Seq[BigInt]]
          lazy val noLocalDateList = 'noLocalDateList.required[Seq[LocalDate]]
          lazy val noLocalDateTimeList = 'noLocalDateTimeList.required[Seq[LocalDateTime]]
          lazy val noLocaleList = 'noLocaleList.required[Seq[Locale]]
          lazy val noLocalTimeList = 'noLocalTimeList.required[Seq[LocalTime]]
          lazy val noMonthDayList = 'noMonthDayList.required[Seq[MonthDay]]
          lazy val noOffsetDateTimeList = 'noOffsetDateTimeList.required[Seq[OffsetDateTime]]
          lazy val noPeriodList = 'noPeriodList.required[Seq[Period]]
          lazy val noUuidList = 'noUuidList.required[Seq[UUID]]
          lazy val noYearList = 'noYearList.required[Seq[Year]]
          lazy val noYearMonthList = 'noYearMonthList.required[Seq[YearMonth]]
          lazy val noZonedDateTimeList = 'noZonedDateTimeList.required[Seq[ZonedDateTime]]
          lazy val noZoneIdList = 'noZoneIdList.required[Seq[ZoneId]]
          lazy val noFiniteDurationList = 'noFiniteDurationList.required[Seq[FiniteDuration]]
        }

        // mapped values
        assertThrows[ConfigException.Missing](MyConfig.noBigDecimal)
        assertThrows[ConfigException.Missing](MyConfig.noBigInt)
        assertThrows[ConfigException.Missing](MyConfig.noLocalDate)
        assertThrows[ConfigException.Missing](MyConfig.noLocalDateTime)
        assertThrows[ConfigException.Missing](MyConfig.noLocale)
        assertThrows[ConfigException.Missing](MyConfig.noLocalTime)
        assertThrows[ConfigException.Missing](MyConfig.noMonthDay)
        assertThrows[ConfigException.Missing](MyConfig.noOffsetDateTime)
        assertThrows[ConfigException.Missing](MyConfig.noPeriod)
        assertThrows[ConfigException.Missing](MyConfig.noUuid)
        assertThrows[ConfigException.Missing](MyConfig.noYear)
        assertThrows[ConfigException.Missing](MyConfig.noYearMonth)
        assertThrows[ConfigException.Missing](MyConfig.noZonedDateTime)
        assertThrows[ConfigException.Missing](MyConfig.noZoneId)
        assertThrows[ConfigException.Missing](MyConfig.noFiniteDuration)

        // mapped list values
        assertThrows[ConfigException.Missing](MyConfig.noBigDecimalList)
        assertThrows[ConfigException.Missing](MyConfig.noBigIntList)
        assertThrows[ConfigException.Missing](MyConfig.noLocalDateList)
        assertThrows[ConfigException.Missing](MyConfig.noLocalDateTimeList)
        assertThrows[ConfigException.Missing](MyConfig.noLocaleList)
        assertThrows[ConfigException.Missing](MyConfig.noLocalTimeList)
        assertThrows[ConfigException.Missing](MyConfig.noMonthDayList)
        assertThrows[ConfigException.Missing](MyConfig.noOffsetDateTimeList)
        assertThrows[ConfigException.Missing](MyConfig.noPeriodList)
        assertThrows[ConfigException.Missing](MyConfig.noUuidList)
        assertThrows[ConfigException.Missing](MyConfig.noYearList)
        assertThrows[ConfigException.Missing](MyConfig.noYearMonthList)
        assertThrows[ConfigException.Missing](MyConfig.noZonedDateTimeList)
        assertThrows[ConfigException.Missing](MyConfig.noZoneIdList)
        assertThrows[ConfigException.Missing](MyConfig.noFiniteDurationList)
      }
    }

    ".optional should:" - {
      "retrieve native values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBoolean = 'aBoolean.optional[Boolean]
          val aDouble = 'aDouble.optional[Double]
          val aDuration = 'aDuration.optional[Duration]
          val aInt = 'aInt.optional[Int]
          val aLong = 'aLong.optional[Long]
          val aString = 'aString.optional[String]
        }

        assert(MyConfig.aBoolean.contains(Configuration.aBoolean))
        assert(MyConfig.aDouble.contains(Configuration.aDouble))
        assert(MyConfig.aDuration.contains(Configuration.aDuration))
        assert(MyConfig.aInt.contains(Configuration.aInt))
        assert(MyConfig.aLong.contains(Configuration.aLong))
        assert(MyConfig.aString.contains(Configuration.aString))
      }

      "retrieve mapped values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBigDecimal = 'aBigDecimal.optional[BigDecimal]
          val aBigInt = 'aBigInt.optional[BigInt]
          val aLocalDate = 'aLocalDate.optional[LocalDate]
          val aLocalDateTime = 'aLocalDateTime.optional[LocalDateTime]
          val aLocale = 'aLocale.optional[Locale]
          val aLocalTime = 'aLocalTime.optional[LocalTime]
          val aMonthDay = 'aMonthDay.optional[MonthDay]
          val aOffsetDateTime = 'aOffsetDateTime.optional[OffsetDateTime]
          val aPeriod = 'aPeriod.optional[Period]
          val aUuid = 'aUuid.optional[UUID]
          val aYear = 'aYear.optional[Year]
          val aYearMonth = 'aYearMonth.optional[YearMonth]
          val aZonedDateTime = 'aZonedDateTime.optional[ZonedDateTime]
          val aZoneId = 'aZoneId.optional[ZoneId]
          val aFiniteDuration = 'aFiniteDuration.optional[FiniteDuration]
        }

        assert(MyConfig.aBigDecimal.contains(Configuration.aBigDecimal))
        assert(MyConfig.aBigInt.contains(Configuration.aBigInt))
        assert(MyConfig.aLocalDate.contains(Configuration.aLocalDate))
        assert(MyConfig.aLocalDateTime.contains(Configuration.aLocalDateTime))
        assert(MyConfig.aLocale.contains(Configuration.aLocale))
        assert(MyConfig.aLocalTime.contains(Configuration.aLocalTime))
        assert(MyConfig.aMonthDay.contains(Configuration.aMonthDay))
        assert(MyConfig.aOffsetDateTime.contains(Configuration.aOffsetDateTime))
        assert(MyConfig.aPeriod.contains(Configuration.aPeriod))
        assert(MyConfig.aUuid.contains(Configuration.aUuid))
        assert(MyConfig.aYear.contains(Configuration.aYear))
        assert(MyConfig.aYearMonth.contains(Configuration.aYearMonth))
        assert(MyConfig.aZonedDateTime.contains(Configuration.aZonedDateTime))
        assert(MyConfig.aZoneId.contains(Configuration.aZoneId))
        assert(MyConfig.aFiniteDuration.contains(Configuration.aFiniteDuration))
      }

      "retrieve native list values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBooleanList = 'aBooleanList.optional[Seq[Boolean]]
          val aDoubleList = 'aDoubleList.optional[Seq[Double]]
          val aDurationList = 'aDurationList.optional[Seq[Duration]]
          val aIntList = 'aIntList.optional[Seq[Int]]
          val aLongList = 'aLongList.optional[Seq[Long]]
          val aStringList = 'aStringList.optional[Seq[String]]
        }

        assert(MyConfig.aBooleanList.contains(Configuration.aBooleanList))
        assert(MyConfig.aDoubleList.contains(Configuration.aDoubleList))
        assert(MyConfig.aDurationList.contains(Configuration.aDurationList))
        assert(MyConfig.aIntList.contains(Configuration.aIntList))
        assert(MyConfig.aLongList.contains(Configuration.aLongList))
        assert(MyConfig.aStringList.contains(Configuration.aStringList))
      }

      "retrieve mapped list values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val aBigDecimalList = 'aBigDecimalList.optional[Seq[BigDecimal]]
          val aBigIntList = 'aBigIntList.optional[Seq[BigInt]]
          val aLocalDateList = 'aLocalDateList.optional[Seq[LocalDate]]
          val aLocalDateTimeList = 'aLocalDateTimeList.optional[Seq[LocalDateTime]]
          val aLocaleList = 'aLocaleList.optional[Seq[Locale]]
          val aLocalTimeList = 'aLocalTimeList.optional[Seq[LocalTime]]
          val aMonthDayList = 'aMonthDayList.optional[Seq[MonthDay]]
          val aOffsetDateTimeList = 'aOffsetDateTimeList.optional[Seq[OffsetDateTime]]
          val aPeriodList = 'aPeriodList.optional[Seq[Period]]
          val aUuidList = 'aUuidList.optional[Seq[UUID]]
          val aYearList = 'aYearList.optional[Seq[Year]]
          val aYearMonthList = 'aYearMonthList.optional[Seq[YearMonth]]
          val aZonedDateTimeList = 'aZonedDateTimeList.optional[Seq[ZonedDateTime]]
          val aZoneIdList = 'aZoneIdList.optional[Seq[ZoneId]]
          val aFiniteDurationList = 'aFiniteDurationList.optional[Seq[FiniteDuration]]
        }

        assert(MyConfig.aBigDecimalList.contains(Configuration.aBigDecimalList))
        assert(MyConfig.aBigIntList.contains(Configuration.aBigIntList))
        assert(MyConfig.aLocalDateList.contains(Configuration.aLocalDateList))
        assert(MyConfig.aLocalDateTimeList.contains(Configuration.aLocalDateTimeList))
        assert(MyConfig.aLocaleList.contains(Configuration.aLocaleList))
        assert(MyConfig.aLocalTimeList.contains(Configuration.aLocalTimeList))
        assert(MyConfig.aMonthDayList.contains(Configuration.aMonthDayList))
        assert(MyConfig.aOffsetDateTimeList.contains(Configuration.aOffsetDateTimeList))
        assert(MyConfig.aPeriodList.contains(Configuration.aPeriodList))
        assert(MyConfig.aUuidList.contains(Configuration.aUuidList))
        assert(MyConfig.aYearList.contains(Configuration.aYearList))
        assert(MyConfig.aYearMonthList.contains(Configuration.aYearMonthList))
        assert(MyConfig.aZonedDateTimeList.contains(Configuration.aZonedDateTimeList))
        assert(MyConfig.aZoneIdList.contains(Configuration.aZoneIdList))
        assert(MyConfig.aFiniteDurationList.contains(Configuration.aFiniteDurationList))
      }

      "retrieve native and mapped values without type parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val aBoolean: Option[Boolean] = 'aBoolean.optional
          val aDouble: Option[Double] = 'aDouble.optional
          val aDuration: Option[Duration] = 'aDuration.optional
          val aInt: Option[Int] = 'aInt.optional
          val aLong: Option[Long] = 'aLong.optional
          val aString: Option[String] = 'aString.optional

          // mapped values
          val aBigDecimal: Option[BigDecimal] = 'aBigDecimal.optional
          val aBigInt: Option[BigInt] = 'aBigInt.optional
          val aLocalDate: Option[LocalDate] = 'aLocalDate.optional
          val aLocalDateTime: Option[LocalDateTime] = 'aLocalDateTime.optional
          val aLocale: Option[Locale] = 'aLocale.optional
          val aLocalTime: Option[LocalTime] = 'aLocalTime.optional
          val aMonthDay: Option[MonthDay] = 'aMonthDay.optional
          val aOffsetDateTime: Option[OffsetDateTime] = 'aOffsetDateTime.optional
          val aPeriod: Option[Period] = 'aPeriod.optional
          val aUuid: Option[UUID] = 'aUuid.optional
          val aYear: Option[Year] = 'aYear.optional
          val aYearMonth: Option[YearMonth] = 'aYearMonth.optional
          val aZonedDateTime: Option[ZonedDateTime] = 'aZonedDateTime.optional
          val aZoneId: Option[ZoneId] = 'aZoneId.optional
          val aFiniteDuration: Option[FiniteDuration] = 'aFiniteDuration.optional
        }

        // native values
        assert(MyConfig.aBoolean.contains(Configuration.aBoolean))
        assert(MyConfig.aDouble.contains(Configuration.aDouble))
        assert(MyConfig.aDuration.contains(Configuration.aDuration))
        assert(MyConfig.aInt.contains(Configuration.aInt))
        assert(MyConfig.aLong.contains(Configuration.aLong))
        assert(MyConfig.aString.contains(Configuration.aString))

        // mapped values
        assert(MyConfig.aBigDecimal.contains(Configuration.aBigDecimal))
        assert(MyConfig.aBigInt.contains(Configuration.aBigInt))
        assert(MyConfig.aLocalDate.contains(Configuration.aLocalDate))
        assert(MyConfig.aLocalDateTime.contains(Configuration.aLocalDateTime))
        assert(MyConfig.aLocale.contains(Configuration.aLocale))
        assert(MyConfig.aLocalTime.contains(Configuration.aLocalTime))
        assert(MyConfig.aMonthDay.contains(Configuration.aMonthDay))
        assert(MyConfig.aOffsetDateTime.contains(Configuration.aOffsetDateTime))
        assert(MyConfig.aPeriod.contains(Configuration.aPeriod))
        assert(MyConfig.aUuid.contains(Configuration.aUuid))
        assert(MyConfig.aYear.contains(Configuration.aYear))
        assert(MyConfig.aYearMonth.contains(Configuration.aYearMonth))
        assert(MyConfig.aZonedDateTime.contains(Configuration.aZonedDateTime))
        assert(MyConfig.aZoneId.contains(Configuration.aZoneId))
        assert(MyConfig.aFiniteDuration.contains(Configuration.aFiniteDuration))
      }

      "retrieve native and mapped list values without type parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native list values
          val aBooleanList: Option[Seq[Boolean]] = 'aBooleanList.optional
          val aDoubleList: Option[Seq[Double]] = 'aDoubleList.optional
          val aDurationList: Option[Seq[Duration]] = 'aDurationList.optional
          val aIntList: Option[Seq[Int]] = 'aIntList.optional
          val aLongList: Option[Seq[Long]] = 'aLongList.optional
          val aStringList: Option[Seq[String]] = 'aStringList.optional

          // mapped list values
          val aBigDecimalList: Option[Seq[BigDecimal]] = 'aBigDecimalList.optional
          val aBigIntList: Option[Seq[BigInt]] = 'aBigIntList.optional
          val aLocalDateList: Option[Seq[LocalDate]] = 'aLocalDateList.optional
          val aLocalDateTimeList: Option[Seq[LocalDateTime]] = 'aLocalDateTimeList.optional
          val aLocaleList: Option[Seq[Locale]] = 'aLocaleList.optional
          val aLocalTimeList: Option[Seq[LocalTime]] = 'aLocalTimeList.optional
          val aMonthDayList: Option[Seq[MonthDay]] = 'aMonthDayList.optional
          val aOffsetDateTimeList: Option[Seq[OffsetDateTime]] = 'aOffsetDateTimeList.optional
          val aPeriodList: Option[Seq[Period]] = 'aPeriodList.optional
          val aUuidList: Option[Seq[UUID]] = 'aUuidList.optional
          val aYearList: Option[Seq[Year]] = 'aYearList.optional
          val aYearMonthList: Option[Seq[YearMonth]] = 'aYearMonthList.optional
          val aZonedDateTimeList: Option[Seq[ZonedDateTime]] = 'aZonedDateTimeList.optional
          val aZoneIdList: Option[Seq[ZoneId]] = 'aZoneIdList.optional
          val aFiniteDurationList: Option[Seq[FiniteDuration]] = 'aFiniteDurationList.optional
        }

        // native list values
        assert(MyConfig.aBooleanList.contains(Configuration.aBooleanList))
        assert(MyConfig.aDoubleList.contains(Configuration.aDoubleList))
        assert(MyConfig.aDurationList.contains(Configuration.aDurationList))
        assert(MyConfig.aIntList.contains(Configuration.aIntList))
        assert(MyConfig.aLongList.contains(Configuration.aLongList))
        assert(MyConfig.aStringList.contains(Configuration.aStringList))

        // mapped list values
        assert(MyConfig.aBigDecimalList.contains(Configuration.aBigDecimalList))
        assert(MyConfig.aBigIntList.contains(Configuration.aBigIntList))
        assert(MyConfig.aLocalDateList.contains(Configuration.aLocalDateList))
        assert(MyConfig.aLocalDateTimeList.contains(Configuration.aLocalDateTimeList))
        assert(MyConfig.aLocaleList.contains(Configuration.aLocaleList))
        assert(MyConfig.aLocalTimeList.contains(Configuration.aLocalTimeList))
        assert(MyConfig.aMonthDayList.contains(Configuration.aMonthDayList))
        assert(MyConfig.aOffsetDateTimeList.contains(Configuration.aOffsetDateTimeList))
        assert(MyConfig.aPeriodList.contains(Configuration.aPeriodList))
        assert(MyConfig.aUuidList.contains(Configuration.aUuidList))
        assert(MyConfig.aYearList.contains(Configuration.aYearList))
        assert(MyConfig.aYearMonthList.contains(Configuration.aYearMonthList))
        assert(MyConfig.aZonedDateTimeList.contains(Configuration.aZonedDateTimeList))
        assert(MyConfig.aZoneIdList.contains(Configuration.aZoneIdList))
        assert(MyConfig.aFiniteDurationList.contains(Configuration.aFiniteDurationList))
      }

      "return None for non-existing keys: native and native list values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val someBoolean = 'someBoolean.optional[Boolean]
          val someDouble = 'someDouble.optional[Double]
          val someDuration = 'someDuration.optional[Duration]
          val someInt = 'someInt.optional[Int]
          val someLong = 'someLong.optional[Long]
          val someString = 'someString.optional[String]

          // native list values
          val someBooleanList = 'someBooleanList.optional[Seq[Boolean]]
          val someDoubleList = 'someDoubleList.optional[Seq[Double]]
          val someDurationList = 'someDurationList.optional[Seq[Duration]]
          val someIntList = 'someIntList.optional[Seq[Int]]
          val someLongList = 'someLongList.optional[Seq[Long]]
          val someStringList = 'someStringList.optional[Seq[String]]
        }

        // native values
        assert(MyConfig.someBoolean.isEmpty)
        assert(MyConfig.someDouble.isEmpty)
        assert(MyConfig.someDuration.isEmpty)
        assert(MyConfig.someInt.isEmpty)
        assert(MyConfig.someLong.isEmpty)
        assert(MyConfig.someString.isEmpty)

        // native list values
        assert(MyConfig.someBooleanList.isEmpty)
        assert(MyConfig.someDoubleList.isEmpty)
        assert(MyConfig.someDurationList.isEmpty)
        assert(MyConfig.someIntList.isEmpty)
        assert(MyConfig.someLongList.isEmpty)
        assert(MyConfig.someStringList.isEmpty)
      }

      "return None for non-existing keys: mapped and mapped list values" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // mapped values
          val someBigDecimal = 'someBigDecimal.optional[BigDecimal]
          val someBigInt = 'someBigInt.optional[BigInt]
          val someLocalDate = 'someLocalDate.optional[LocalDate]
          val someLocalDateTime = 'someLocalDateTime.optional[LocalDateTime]
          val someLocale = 'someLocale.optional[Locale]
          val someLocalTime = 'someLocalTime.optional[LocalTime]
          val someMonthDay = 'someMonthDay.optional[MonthDay]
          val someOffsetDateTime = 'someOffsetDateTime.optional[OffsetDateTime]
          val somePeriod = 'somePeriod.optional[Period]
          val someUuid = 'someUuid.optional[UUID]
          val someYear = 'someYear.optional[Year]
          val someYearMonth = 'someYearMonth.optional[YearMonth]
          val someZonedDateTime = 'someZonedDateTime.optional[ZonedDateTime]
          val someZoneId = 'someZoneId.optional[ZoneId]
          val someFiniteDuration = 'someFiniteDuration.optional[FiniteDuration]

          // mapped list values
          val someBigDecimalList = 'someBigDecimalList.optional[Seq[BigDecimal]]
          val someBigIntList = 'someBigIntList.optional[Seq[BigInt]]
          val someLocalDateList = 'someLocalDateList.optional[Seq[LocalDate]]
          val someLocalDateTimeList = 'someLocalDateTimeList.optional[Seq[LocalDateTime]]
          val someLocaleList = 'someLocaleList.optional[Seq[Locale]]
          val someLocalTimeList = 'someLocalTimeList.optional[Seq[LocalTime]]
          val someMonthDayList = 'someMonthDayList.optional[Seq[MonthDay]]
          val someOffsetDateTimeList = 'someOffsetDateTimeList.optional[Seq[OffsetDateTime]]
          val somePeriodList = 'somePeriodList.optional[Seq[Period]]
          val someUuidList = 'someUuidList.optional[Seq[UUID]]
          val someYearList = 'someYearList.optional[Seq[Year]]
          val someYearMonthList = 'someYearMonthList.optional[Seq[YearMonth]]
          val someZonedDateTimeList = 'someZonedDateTimeList.optional[Seq[ZonedDateTime]]
          val someZoneIdList = 'someZoneIdList.optional[Seq[ZoneId]]
          val someFiniteDurationList = 'someFiniteDurationList.optional[Seq[FiniteDuration]]
        }

        // mapped values
        assert(MyConfig.someBigDecimal.isEmpty)
        assert(MyConfig.someBigInt.isEmpty)
        assert(MyConfig.someLocalDate.isEmpty)
        assert(MyConfig.someLocalDateTime.isEmpty)
        assert(MyConfig.someLocale.isEmpty)
        assert(MyConfig.someLocalTime.isEmpty)
        assert(MyConfig.someMonthDay.isEmpty)
        assert(MyConfig.someOffsetDateTime.isEmpty)
        assert(MyConfig.somePeriod.isEmpty)
        assert(MyConfig.someUuid.isEmpty)
        assert(MyConfig.someYear.isEmpty)
        assert(MyConfig.someYearMonth.isEmpty)
        assert(MyConfig.someZonedDateTime.isEmpty)
        assert(MyConfig.someZoneId.isEmpty)
        assert(MyConfig.someFiniteDuration.isEmpty)

        // mapped list values
        assert(MyConfig.someBigDecimalList.isEmpty)
        assert(MyConfig.someBigIntList.isEmpty)
        assert(MyConfig.someLocalDateList.isEmpty)
        assert(MyConfig.someLocalDateTimeList.isEmpty)
        assert(MyConfig.someLocaleList.isEmpty)
        assert(MyConfig.someLocalTimeList.isEmpty)
        assert(MyConfig.someMonthDayList.isEmpty)
        assert(MyConfig.someOffsetDateTimeList.isEmpty)
        assert(MyConfig.somePeriodList.isEmpty)
        assert(MyConfig.someUuidList.isEmpty)
        assert(MyConfig.someYearList.isEmpty)
        assert(MyConfig.someYearMonthList.isEmpty)
        assert(MyConfig.someZonedDateTimeList.isEmpty)
        assert(MyConfig.someZoneIdList.isEmpty)
        assert(MyConfig.someFiniteDurationList.isEmpty)
      }
    }

    ".orElse should:" - {
      "retrieve existing keys without calling default parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val aBoolean: Boolean = 'aBoolean orElse fail
          val aDouble: Double = 'aDouble orElse fail
          val aDuration: Duration = 'aDuration orElse fail
          val aInt: Int = 'aInt orElse fail
          val aLong: Long = 'aLong orElse fail
          val aString: String = 'aString orElse fail

          // mapped values
          val aBigInt: BigInt = 'aBigInt orElse fail
          val aLocale: Locale = 'aLocale orElse fail
          val aPeriod: Period = 'aPeriod orElse fail
          val aUuid: UUID = 'aUuid orElse fail
          val aFiniteDuration: FiniteDuration = 'aFiniteDuration orElse fail

          // native list values
          val aBooleanList: Seq[Boolean] = 'aBooleanList orElse fail
          val aDoubleList: Seq[Double] = 'aDoubleList orElse fail
          val aDurationList: Seq[Duration] = 'aDurationList orElse fail
          val aIntList: Seq[Int] = 'aIntList orElse fail
          val aLongList: Seq[Long] = 'aLongList orElse fail
          val aStringList: Seq[String] = 'aStringList orElse fail

          // mapped list values
          val aBigIntList: Seq[BigInt] = 'aBigIntList orElse fail
          val aLocaleList: Seq[Locale] = 'aLocaleList orElse fail
          val aPeriodList: Seq[Period] = 'aPeriodList orElse fail
          val aUuidList: Seq[UUID] = 'aUuidList orElse fail
          val aFiniteDurationList: Seq[FiniteDuration] = 'aFiniteDurationList orElse fail
        }

        // native values
        assert(MyConfig.aBoolean == Configuration.aBoolean)
        assert(MyConfig.aDouble == Configuration.aDouble)
        assert(MyConfig.aDuration == Configuration.aDuration)
        assert(MyConfig.aInt == Configuration.aInt)
        assert(MyConfig.aLong == Configuration.aLong)
        assert(MyConfig.aString == Configuration.aString)

        // mapped values
        assert(MyConfig.aBigInt == Configuration.aBigInt)
        assert(MyConfig.aLocale == Configuration.aLocale)
        assert(MyConfig.aPeriod == Configuration.aPeriod)
        assert(MyConfig.aUuid == Configuration.aUuid)
        assert(MyConfig.aFiniteDuration == Configuration.aFiniteDuration)

        // native list values
        assert(MyConfig.aBooleanList == Configuration.aBooleanList)
        assert(MyConfig.aDoubleList == Configuration.aDoubleList)
        assert(MyConfig.aDurationList == Configuration.aDurationList)
        assert(MyConfig.aIntList == Configuration.aIntList)
        assert(MyConfig.aLongList == Configuration.aLongList)
        assert(MyConfig.aStringList == Configuration.aStringList)

        // mapped list values
        assert(MyConfig.aBigIntList == Configuration.aBigIntList)
        assert(MyConfig.aLocaleList == Configuration.aLocaleList)
        assert(MyConfig.aPeriodList == Configuration.aPeriodList)
        assert(MyConfig.aUuidList == Configuration.aUuidList)
        assert(MyConfig.aFiniteDurationList == Configuration.aFiniteDurationList)
      }

      "retrieve existing keys without type parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val aBoolean = 'aBoolean orElse Default.someTrueBoolean
          val aDouble = 'aDouble orElse Default.someDouble
          val aDuration = 'aDuration orElse Default.someDuration
          val aInt = 'aInt orElse Default.someInt
          val aLong = 'aLong orElse Default.someLong
          val aString = 'aString orElse Default.someString

          // mapped values
          val aBigInt = 'aBigInt orElse Default.someBigInt
          val aLocale = 'aLocale orElse Default.someLocale
          val aPeriod = 'aPeriod orElse Default.somePeriod
          val aUuid = 'aUuid orElse Default.someUuid
          val aFiniteDuration = 'aFiniteDuration orElse Default.someFiniteDuration

          // native list values
          val aBooleanList = 'aBooleanList orElse Default.someBooleanList
          val aDoubleList = 'aDoubleList orElse Default.someDoubleList
          val aDurationList = 'aDurationList orElse Default.someDurationList
          val aIntList = 'aIntList orElse Default.someIntList
          val aLongList = 'aLongList orElse Default.someLongList
          val aStringList = 'aStringList orElse Default.someStringList

          // mapped list values
          val aBigIntList = 'aBigIntList orElse Default.someBigIntList
          val aLocaleList = 'aLocaleList orElse Default.someLocaleList
          val aPeriodList = 'aPeriodList orElse Default.somePeriodList
          val aUuidList = 'aUuidList orElse Default.someUuidList
          val aFiniteDurationList = 'aFiniteDurationList orElse Default.someFiniteDurationList
        }

        // native values
        assert(MyConfig.aBoolean == Configuration.aBoolean)
        assert(MyConfig.aDouble == Configuration.aDouble)
        assert(MyConfig.aDuration == Configuration.aDuration)
        assert(MyConfig.aInt == Configuration.aInt)
        assert(MyConfig.aLong == Configuration.aLong)
        assert(MyConfig.aString == Configuration.aString)

        // mapped values
        assert(MyConfig.aBigInt == Configuration.aBigInt)
        assert(MyConfig.aLocale == Configuration.aLocale)
        assert(MyConfig.aPeriod == Configuration.aPeriod)
        assert(MyConfig.aUuid == Configuration.aUuid)
        assert(MyConfig.aFiniteDuration == Configuration.aFiniteDuration)

        // native list values
        assert(MyConfig.aBooleanList == Configuration.aBooleanList)
        assert(MyConfig.aDoubleList == Configuration.aDoubleList)
        assert(MyConfig.aDurationList == Configuration.aDurationList)
        assert(MyConfig.aIntList == Configuration.aIntList)
        assert(MyConfig.aLongList == Configuration.aLongList)
        assert(MyConfig.aStringList == Configuration.aStringList)

        // mapped list values
        assert(MyConfig.aBigIntList == Configuration.aBigIntList)
        assert(MyConfig.aLocaleList == Configuration.aLocaleList)
        assert(MyConfig.aPeriodList == Configuration.aPeriodList)
        assert(MyConfig.aUuidList == Configuration.aUuidList)
        assert(MyConfig.aFiniteDurationList == Configuration.aFiniteDurationList)
      }

      "return default value for non-existing keys" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val someTrueBoolean = 'someTrueBoolean orElse Default.someTrueBoolean
          val someFalseBoolean = 'someFalseBoolean orElse Default.someFalseBoolean
          val someDouble = 'someDouble orElse Default.someDouble
          val someDuration = 'someDuration orElse Default.someDuration
          val someInt = 'someInt orElse Default.someInt
          val someLong = 'someLong orElse Default.someLong
          val someString = 'someString orElse Default.someString

          // mapped values
          val someBigInt = 'someBigInt orElse Default.someBigInt
          val someLocale = 'someLocale orElse Default.someLocale
          val somePeriod = 'somePeriod orElse Default.somePeriod
          val someUuid = 'someUuid orElse Default.someUuid
          val someFiniteDuration = 'someFiniteDuration orElse Default.someFiniteDuration

          // native list values
          val someBooleanList = 'someBooleanList orElse Default.someBooleanList
          val someDoubleList = 'someDoubleList orElse Default.someDoubleList
          val someDurationList = 'someDurationList orElse Default.someDurationList
          val someIntList = 'someIntList orElse Default.someIntList
          val someLongList = 'someLongList orElse Default.someLongList
          val someStringList = 'someStringList orElse Default.someStringList

          // mapped list values
          val someBigIntList = 'someBigIntList orElse Default.someBigIntList
          val someLocaleList = 'someLocaleList orElse Default.someLocaleList
          val somePeriodList = 'somePeriodList orElse Default.somePeriodList
          val someUuidList = 'someUuidList orElse Default.someUuidList
          val someFiniteDurationList = 'someFiniteDurationList orElse Default.someFiniteDurationList
        }

        // native values
        assert(MyConfig.someTrueBoolean == Default.someTrueBoolean)
        assert(MyConfig.someFalseBoolean == Default.someFalseBoolean)
        assert(MyConfig.someDouble == Default.someDouble)
        assert(MyConfig.someDuration == Default.someDuration)
        assert(MyConfig.someInt == Default.someInt)
        assert(MyConfig.someLong == Default.someLong)
        assert(MyConfig.someString == Default.someString)

        // mapped values
        assert(MyConfig.someBigInt == Default.someBigInt)
        assert(MyConfig.someLocale == Default.someLocale)
        assert(MyConfig.somePeriod == Default.somePeriod)
        assert(MyConfig.someUuid == Default.someUuid)
        assert(MyConfig.someFiniteDuration == Default.someFiniteDuration)

        // native list values
        assert(MyConfig.someBooleanList == Default.someBooleanList)
        assert(MyConfig.someDoubleList == Default.someDoubleList)
        assert(MyConfig.someDurationList == Default.someDurationList)
        assert(MyConfig.someIntList == Default.someIntList)
        assert(MyConfig.someLongList == Default.someLongList)
        assert(MyConfig.someStringList == Default.someStringList)

        // mapped list values
        assert(MyConfig.someBigIntList == Default.someBigIntList)
        assert(MyConfig.someLocaleList == Default.someLocaleList)
        assert(MyConfig.somePeriodList == Default.somePeriodList)
        assert(MyConfig.someUuidList == Default.someUuidList)
        assert(MyConfig.someFiniteDurationList == Default.someFiniteDurationList)
      }
    }

    "convenience sorthands should:" - {
      "retrieve existing keys" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val aFalseBoolean = 'aBoolean.orFalse
          val aTrueBoolean = 'aBoolean.orTrue
          val aInt = 'aInt.orZero
          val aString = 'aString.orBlank

          // native list values
          val aBooleanList = 'aBooleanList.orEmpty[Boolean]
          val aDoubleList = 'aDoubleList.orEmpty[Double]
          val aDurationList = 'aDurationList.orEmpty[Duration]
          val aIntList = 'aIntList.orEmpty[Int]
          val aLongList = 'aLongList.orEmpty[Long]
          val aStringList = 'aStringList.orEmpty[String]

          // mapped list values
          val aBigDecimalList = 'aBigDecimalList.orEmpty[BigDecimal]
          val aBigIntList = 'aBigIntList.orEmpty[BigInt]
          val aLocalDateList = 'aLocalDateList.orEmpty[LocalDate]
          val aLocalDateTimeList = 'aLocalDateTimeList.orEmpty[LocalDateTime]
          val aLocaleList = 'aLocaleList.orEmpty[Locale]
          val aLocalTimeList = 'aLocalTimeList.orEmpty[LocalTime]
          val aMonthDayList = 'aMonthDayList.orEmpty[MonthDay]
          val aOffsetDateTimeList = 'aOffsetDateTimeList.orEmpty[OffsetDateTime]
          val aPeriodList = 'aPeriodList.orEmpty[Period]
          val aUuidList = 'aUuidList.orEmpty[UUID]
          val aYearList = 'aYearList.orEmpty[Year]
          val aYearMonthList = 'aYearMonthList.orEmpty[YearMonth]
          val aZonedDateTimeList = 'aZonedDateTimeList.orEmpty[ZonedDateTime]
          val aZoneIdList = 'aZoneIdList.orEmpty[ZoneId]
          val aFiniteDurationList = 'aFiniteDurationList.orEmpty[FiniteDuration]
        }

        // native values
        assert(MyConfig.aFalseBoolean == Configuration.aBoolean)
        assert(MyConfig.aTrueBoolean == Configuration.aBoolean)
        assert(MyConfig.aInt == Configuration.aInt)
        assert(MyConfig.aString == Configuration.aString)

        // native list values
        assert(MyConfig.aBooleanList == Configuration.aBooleanList)
        assert(MyConfig.aDoubleList == Configuration.aDoubleList)
        assert(MyConfig.aDurationList == Configuration.aDurationList)
        assert(MyConfig.aIntList == Configuration.aIntList)
        assert(MyConfig.aLongList == Configuration.aLongList)
        assert(MyConfig.aStringList == Configuration.aStringList)

        // mapped list values
        assert(MyConfig.aBigDecimalList == Configuration.aBigDecimalList)
        assert(MyConfig.aBigIntList == Configuration.aBigIntList)
        assert(MyConfig.aLocalDateList == Configuration.aLocalDateList)
        assert(MyConfig.aLocalDateTimeList == Configuration.aLocalDateTimeList)
        assert(MyConfig.aLocaleList == Configuration.aLocaleList)
        assert(MyConfig.aLocalTimeList == Configuration.aLocalTimeList)
        assert(MyConfig.aMonthDayList == Configuration.aMonthDayList)
        assert(MyConfig.aOffsetDateTimeList == Configuration.aOffsetDateTimeList)
        assert(MyConfig.aPeriodList == Configuration.aPeriodList)
        assert(MyConfig.aUuidList == Configuration.aUuidList)
        assert(MyConfig.aYearList == Configuration.aYearList)
        assert(MyConfig.aYearMonthList == Configuration.aYearMonthList)
        assert(MyConfig.aZonedDateTimeList == Configuration.aZonedDateTimeList)
        assert(MyConfig.aZoneIdList == Configuration.aZoneIdList)
        assert(MyConfig.aFiniteDurationList == Configuration.aFiniteDurationList)
      }

      "retrieve existing keys without type parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native list values
          val aBooleanList: Seq[Boolean] = 'aBooleanList.orEmpty
          val aDoubleList: Seq[Double] = 'aDoubleList.orEmpty
          val aDurationList: Seq[Duration] = 'aDurationList.orEmpty
          val aIntList: Seq[Int] = 'aIntList.orEmpty
          val aLongList: Seq[Long] = 'aLongList.orEmpty
          val aStringList: Seq[String] = 'aStringList.orEmpty

          // mapped list values
          val aBigDecimalList: Seq[BigDecimal] = 'aBigDecimalList.orEmpty
          val aBigIntList: Seq[BigInt] = 'aBigIntList.orEmpty
          val aLocalDateList: Seq[LocalDate] = 'aLocalDateList.orEmpty
          val aLocalDateTimeList: Seq[LocalDateTime] = 'aLocalDateTimeList.orEmpty
          val aLocaleList: Seq[Locale] = 'aLocaleList.orEmpty
          val aLocalTimeList: Seq[LocalTime] = 'aLocalTimeList.orEmpty
          val aMonthDayList: Seq[MonthDay] = 'aMonthDayList.orEmpty
          val aOffsetDateTimeList: Seq[OffsetDateTime] = 'aOffsetDateTimeList.orEmpty
          val aPeriodList: Seq[Period] = 'aPeriodList.orEmpty
          val aUuidList: Seq[UUID] = 'aUuidList.orEmpty
          val aYearList: Seq[Year] = 'aYearList.orEmpty
          val aYearMonthList: Seq[YearMonth] = 'aYearMonthList.orEmpty
          val aZonedDateTimeList: Seq[ZonedDateTime] = 'aZonedDateTimeList.orEmpty
          val aZoneIdList: Seq[ZoneId] = 'aZoneIdList.orEmpty
          val aFiniteDurationList: Seq[FiniteDuration] = 'aFiniteDurationList.orEmpty
        }

        // native list values
        assert(MyConfig.aBooleanList == Configuration.aBooleanList)
        assert(MyConfig.aDoubleList == Configuration.aDoubleList)
        assert(MyConfig.aDurationList == Configuration.aDurationList)
        assert(MyConfig.aIntList == Configuration.aIntList)
        assert(MyConfig.aLongList == Configuration.aLongList)
        assert(MyConfig.aStringList == Configuration.aStringList)

        // mapped list values
        assert(MyConfig.aBigDecimalList == Configuration.aBigDecimalList)
        assert(MyConfig.aBigIntList == Configuration.aBigIntList)
        assert(MyConfig.aLocalDateList == Configuration.aLocalDateList)
        assert(MyConfig.aLocalDateTimeList == Configuration.aLocalDateTimeList)
        assert(MyConfig.aLocaleList == Configuration.aLocaleList)
        assert(MyConfig.aLocalTimeList == Configuration.aLocalTimeList)
        assert(MyConfig.aMonthDayList == Configuration.aMonthDayList)
        assert(MyConfig.aOffsetDateTimeList == Configuration.aOffsetDateTimeList)
        assert(MyConfig.aPeriodList == Configuration.aPeriodList)
        assert(MyConfig.aUuidList == Configuration.aUuidList)
        assert(MyConfig.aYearList == Configuration.aYearList)
        assert(MyConfig.aYearMonthList == Configuration.aYearMonthList)
        assert(MyConfig.aZonedDateTimeList == Configuration.aZonedDateTimeList)
        assert(MyConfig.aZoneIdList == Configuration.aZoneIdList)
        assert(MyConfig.aFiniteDurationList == Configuration.aFiniteDurationList)
      }

      "return default value for non-existing keys" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native values
          val noFalseBoolean = 'noBoolean.orFalse
          val noTrueBoolean = 'noBoolean.orTrue
          val noInt = 'noInt.orZero
          val noString = 'noString.orBlank

          // native list values
          val noBooleanList = 'noBooleanList.orEmpty[Boolean]
          val noDoubleList = 'noDoubleList.orEmpty[Double]
          val noDurationList = 'noDurationList.orEmpty[Duration]
          val noIntList = 'noIntList.orEmpty[Int]
          val noLongList = 'noLongList.orEmpty[Long]
          val noStringList = 'noStringList.orEmpty[String]

          // mapped list values
          val noBigDecimalList = 'noBigDecimalList.orEmpty[BigDecimal]
          val noBigIntList = 'noBigIntList.orEmpty[BigInt]
          val noLocalDateList = 'noLocalDateList.orEmpty[LocalDate]
          val noLocalDateTimeList = 'noLocalDateTimeList.orEmpty[LocalDateTime]
          val noLocaleList = 'noLocaleList.orEmpty[Locale]
          val noLocalTimeList = 'noLocalTimeList.orEmpty[LocalTime]
          val noMonthDayList = 'noMonthDayList.orEmpty[MonthDay]
          val noOffsetDateTimeList = 'noOffsetDateTimeList.orEmpty[OffsetDateTime]
          val noPeriodList = 'noPeriodList.orEmpty[Period]
          val noUuidList = 'noUuidList.orEmpty[UUID]
          val noYearList = 'noYearList.orEmpty[Year]
          val noYearMonthList = 'noYearMonthList.orEmpty[YearMonth]
          val noZonedDateTimeList = 'noZonedDateTimeList.orEmpty[ZonedDateTime]
          val noZoneIdList = 'noZoneIdList.orEmpty[ZoneId]
          val noFiniteDurationList = 'noFiniteDurationList.orEmpty[FiniteDuration]
        }

        // native values
        assert(MyConfig.noFalseBoolean == false)
        assert(MyConfig.noTrueBoolean == true)
        assert(MyConfig.noInt == 0)
        assert(MyConfig.noString == "")

        // native list values
        assert(MyConfig.noBooleanList.isEmpty)
        assert(MyConfig.noDoubleList.isEmpty)
        assert(MyConfig.noDurationList.isEmpty)
        assert(MyConfig.noIntList.isEmpty)
        assert(MyConfig.noLongList.isEmpty)
        assert(MyConfig.noStringList.isEmpty)

        // mapped list values
        assert(MyConfig.noBigDecimalList.isEmpty)
        assert(MyConfig.noBigIntList.isEmpty)
        assert(MyConfig.noLocalDateList.isEmpty)
        assert(MyConfig.noLocalDateTimeList.isEmpty)
        assert(MyConfig.noLocaleList.isEmpty)
        assert(MyConfig.noLocalTimeList.isEmpty)
        assert(MyConfig.noMonthDayList.isEmpty)
        assert(MyConfig.noOffsetDateTimeList.isEmpty)
        assert(MyConfig.noPeriodList.isEmpty)
        assert(MyConfig.noUuidList.isEmpty)
        assert(MyConfig.noYearList.isEmpty)
        assert(MyConfig.noYearMonthList.isEmpty)
        assert(MyConfig.noZonedDateTimeList.isEmpty)
        assert(MyConfig.noZoneIdList.isEmpty)
        assert(MyConfig.noFiniteDurationList.isEmpty)
      }

      "return default value for non-existing keys without type parameter" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          // native list values
          val noBooleanList: Seq[Boolean] = 'noBooleanList.orEmpty
          val noDoubleList: Seq[Double] = 'noDoubleList.orEmpty
          val noDurationList: Seq[Duration] = 'noDurationList.orEmpty
          val noIntList: Seq[Int] = 'noIntList.orEmpty
          val noLongList: Seq[Long] = 'noLongList.orEmpty
          val noStringList: Seq[String] = 'noStringList.orEmpty

          // mapped list values
          val noBigDecimalList: Seq[BigDecimal] = 'noBigDecimalList.orEmpty
          val noBigIntList: Seq[BigInt] = 'noBigIntList.orEmpty
          val noLocalDateList: Seq[LocalDate] = 'noLocalDateList.orEmpty
          val noLocalDateTimeList: Seq[LocalDateTime] = 'noLocalDateTimeList.orEmpty
          val noLocaleList: Seq[Locale] = 'noLocaleList.orEmpty
          val noLocalTimeList: Seq[LocalTime] = 'noLocalTimeList.orEmpty
          val noMonthDayList: Seq[MonthDay] = 'noMonthDayList.orEmpty
          val noOffsetDateTimeList: Seq[OffsetDateTime] = 'noOffsetDateTimeList.orEmpty
          val noPeriodList: Seq[Period] = 'noPeriodList.orEmpty
          val noUuidList: Seq[UUID] = 'noUuidList.orEmpty
          val noYearList: Seq[Year] = 'noYearList.orEmpty
          val noYearMonthList: Seq[YearMonth] = 'noYearMonthList.orEmpty
          val noZonedDateTimeList: Seq[ZonedDateTime] = 'noZonedDateTimeList.orEmpty
          val noZoneIdList: Seq[ZoneId] = 'noZoneIdList.orEmpty
          val noFiniteDurationList: Seq[FiniteDuration] = 'noFiniteDurationList.orEmpty
        }

        // native list values
        assert(MyConfig.noBooleanList.isEmpty)
        assert(MyConfig.noDoubleList.isEmpty)
        assert(MyConfig.noDurationList.isEmpty)
        assert(MyConfig.noIntList.isEmpty)
        assert(MyConfig.noLongList.isEmpty)
        assert(MyConfig.noStringList.isEmpty)

        // mapped list values
        assert(MyConfig.noBigDecimalList.isEmpty)
        assert(MyConfig.noBigIntList.isEmpty)
        assert(MyConfig.noLocalDateList.isEmpty)
        assert(MyConfig.noLocalDateTimeList.isEmpty)
        assert(MyConfig.noLocaleList.isEmpty)
        assert(MyConfig.noLocalTimeList.isEmpty)
        assert(MyConfig.noMonthDayList.isEmpty)
        assert(MyConfig.noOffsetDateTimeList.isEmpty)
        assert(MyConfig.noPeriodList.isEmpty)
        assert(MyConfig.noUuidList.isEmpty)
        assert(MyConfig.noYearList.isEmpty)
        assert(MyConfig.noYearMonthList.isEmpty)
        assert(MyConfig.noZonedDateTimeList.isEmpty)
        assert(MyConfig.noZoneIdList.isEmpty)
        assert(MyConfig.noFiniteDurationList.isEmpty)
      }
    }

    ".path should:" - {
      "return correct path" in new Context {
        object MyConfig extends Config(typesafeConfig) {
          val p = path('x)

          object A extends Config('a) {
            val p = path('y)

            object B extends Config('b) {
              val p = path('z)

              object C extends Config('c) {
                val p = path('w)
              }
            }
          }
        }

        assert(MyConfig.p == "x")
        assert(MyConfig.A.p == "a.y")
        assert(MyConfig.A.B.p == "a.b.z")
        assert(MyConfig.A.B.C.p == "a.b.c.w")
      }
    }

    "ConfigOps should:" - {
      "support keys with arbitrary values" in new Context {
        val key = Symbol("a.very-complicated:key_name!")
        val noKey = Symbol("also#a.very-complicated:key_name that doesn't exist!")

        val result = "ok"

        whenConfig(key, result)

        object MyConfig extends Config(typesafeConfig) {
          val a: String = key.required
          val b = noKey.optional[String]
        }

        assert(MyConfig.a == result)
        assert(MyConfig.b.isEmpty)
      }
    }

    "Reader should:" - {
      "create custom readers by mapping on existing ones" in new Context {
        import scail.commons.util.Config.Reader

        case class Age(age: Int)
        case class Name(name: String)

        implicit val ageReader = Reader.int.map(Age.apply)
        implicit val nameReader = Reader.string.map(Name.apply)

        object MyConfig extends Config(typesafeConfig) {
          val myName: Name = 'aString.required
          val myAge = 'aInt.optional[Age]
          val friendNames: Seq[Name] = 'aStringList.required
          val friendAges = 'aIntList.required[Seq[Age]]
          val petNames = 'noStringList.orEmpty[Name]
        }

        assert(MyConfig.myName.name == Configuration.aString)
        assert(MyConfig.myAge exists (_.age == Configuration.aInt))
        assert(MyConfig.friendNames.map(_.name) == Configuration.aStringList)
        assert(MyConfig.friendAges.map(_.age) == Configuration.aIntList)
        assert(MyConfig.petNames.isEmpty)
      }

      "create custom readers for composite data types" in new Context {
        import scail.commons.util.Config.Reader

        case class MyClass(
          aInt: Int
        , noBoolean: Option[Boolean]
        , aString: Option[String]
        , aUuidList: Seq[UUID]
        )

        object MyClass {
          implicit val myClassReader = Reader { implicit config =>
            MyClass('aInt.required, 'noBoolean.optional, 'aString.optional, 'aUuidList.required)
          }
        }

        whenConfig("myClass.aInt", Configuration.aInt)
        whenConfig("myClass.aString", Configuration.aString)
        whenConfig.uuids("myClass.aUuidList", Configuration.aUuidList)

        object MyConfig extends Config(typesafeConfig) {
          val myClass: MyClass = 'myClass.required
        }

        assert(MyConfig.myClass.aInt == Configuration.aInt)
        assert(MyConfig.myClass.noBoolean.isEmpty)
        assert(MyConfig.myClass.aString.contains(Configuration.aString))
        assert(MyConfig.myClass.aUuidList == Configuration.aUuidList)
      }

      "support nested custom readers" in new Context {
        import scail.commons.util.Config.Reader

        case class Age(age: Int)
        case class Name(name: String)

        implicit val ageReader = Reader.int.map(Age.apply)
        implicit val nameReader = Reader.string.map(Name.apply)

        case class Person(name: Name, age: Age)

        object Person {
          implicit val personReader = Reader { implicit config =>
            Person('name.required, 'age.required)
          }
        }

        case class MyClass(name: Name, age: Age, seq: Seq[Name], self: Person, friends: Seq[Person])

        object MyClass {
          implicit val myClassReader = Reader { implicit config =>
            MyClass(
              'name.required
            , 'age.required
            , 'seq.required
            , 'self.required
            , 'friends.orEmpty
            )
          }
        }

        val person = Person(Name(Configuration.aString), Age(Configuration.aInt))

        whenConfig("myClass.name", person.name.name)
        whenConfig("myClass.age", person.age.age)
        whenConfig.strings("myClass.seq", Configuration.aStringList)
        whenConfig("myClass.self.name", person.name.name)
        whenConfig("myClass.self.age", person.age.age)
        whenConfig("myClass.friends", Seq(typesafeConfig, typesafeConfig))
        whenConfig('name, person.name.name)
        whenConfig('age, person.age.age)

        object MyConfig extends Config(typesafeConfig) {
          val myClass: MyClass = 'myClass.required
        }

        assert(MyConfig.myClass.name == person.name)
        assert(MyConfig.myClass.age == person.age)
        assert(MyConfig.myClass.self == person)
        assert(MyConfig.myClass.seq.map(_.name) == Configuration.aStringList)
        assert(MyConfig.myClass.friends == Seq(person, person))
      }
    }
  }

  class Context extends MockConfig {
    // shared objects
    object Configuration {
      // native values
      val aBoolean = true
      val aDouble = math.Pi
      val aDuration = Duration.ofDays(15)
      val aInt = 42
      val aLong = 2305843009213693951L
      val aString = "test"

      // mapped values
      val aBigDecimal = BigDecimal("0.3")
      val aBigInt = BigInt("9223372036854775808")
      val aLocalDate = LocalDate.now
      val aLocalDateTime = LocalDateTime.now
      val aLocale = Locale.CANADA
      val aLocalTime = LocalTime.now
      val aMonthDay = MonthDay.now
      val aOffsetDateTime = OffsetDateTime.now
      val aPeriod = Period.ofWeeks(2)
      val aUuid = UUID.randomUUID
      val aYear = Year.now
      val aYearMonth = YearMonth.now
      val aZonedDateTime = ZonedDateTime.now
      val aZoneId = ZoneId.systemDefault
      val aFiniteDuration = sDuration.fromNanos(1000)

      // native list values
      val aBooleanList = Seq(true, true, false)
      val aDoubleList = Seq(math.Pi, math.E)
      val aDurationList = Seq(Duration.ofDays(21), Duration.ofHours(12))
      val aIntList = Seq(4, 8, 15, 16, 23, 42)
      val aLongList = Seq(Long.MaxValue, Long.MinValue)
      val aStringList = Seq("sol", "terra", "luna")

      // mapped list values
      val aBigDecimalList = Seq(BigDecimal("0.3"), BigDecimal("6.022140857e23"))
      val aBigIntList = Seq(BigInt("9223372036854775808"), BigInt("-9223372036854775809"))
      val aLocalDateList = Seq(LocalDate.now, LocalDate.MAX, LocalDate.MIN)
      val aLocalDateTimeList = Seq(LocalDateTime.now, LocalDateTime.MAX, LocalDateTime.MIN)
      val aLocaleList = Seq(Locale.FRANCE, Locale.GERMANY, Locale.UK)
      val aLocalTimeList = Seq(LocalTime.now, LocalTime.MAX, LocalTime.MIN, LocalTime.MIDNIGHT)
      val aMonthDayList = Seq(MonthDay.now, MonthDay.of(12, 31))
      val aOffsetDateTimeList = Seq(OffsetDateTime.now, OffsetDateTime.MAX, OffsetDateTime.MIN)
      val aPeriodList = Seq(Period.ofWeeks(2), Period.ZERO)
      val aUuidList = Seq(UUID.randomUUID, UUID.randomUUID)
      val aYearList = Seq(Year.now, Year.of(3001), Year.of(-6000))
      val aYearMonthList = Seq(YearMonth.now, YearMonth.of(2001, 1))
      val aZonedDateTimeList = Seq(
        ZonedDateTime.now
      , ZonedDateTime.parse("2019-01-01T00:00:00.0-05:00[America/Toronto]")
      )
      val aZoneIdList = Seq(ZoneId.systemDefault, ZoneId.of("UTC"))
      val aFiniteDurationList = Seq(sDuration.fromNanos(1000 * 1000), sDuration.Zero)
    }

    object Default {
      // native values
      val someTrueBoolean = true
      val someFalseBoolean = false
      val someDouble = 2.0
      val someDuration = Duration.ofMinutes(3)
      val someInt = -40
      val someLong = 147L
      val someString = "not me"

      // mapped values
      val someBigInt = BigInt("1234567890")
      val someLocale = Locale.CANADA_FRENCH
      val somePeriod = Period.ofYears(1000)
      val someUuid = UUID.randomUUID
      val someFiniteDuration = sDuration.fromNanos(12345)

      // native list values
      val someBooleanList = Seq(false, true, false)
      val someDoubleList = Seq(1.23, 4.56, 7.89)
      val someDurationList = Seq(Duration.ofSeconds(20), Duration.ofHours(8))
      val someIntList = Seq(2, 3, 5, 7, 11, 13)
      val someLongList = Seq(111L, 333L, 555L)
      val someStringList = Seq("aba", "aca", "ada", "afa")

      // mapped list values
      val someBigIntList = Seq(BigInt("999888777666"), BigInt("111222333444"))
      val someLocaleList = Seq(Locale.ITALY, Locale.JAPAN, Locale.US)
      val somePeriodList = Seq(Period.ofYears(4), Period.ofMonths(6))
      val someUuidList = Seq(UUID.randomUUID, UUID.randomUUID, UUID.randomUUID)
      val someFiniteDurationList = Seq(sDuration.fromNanos(98765), sDuration.fromNanos(76543210))
    }

    // common expectations
    // native values
    whenConfig('aBoolean, Configuration.aBoolean)
    whenConfig('aDouble, Configuration.aDouble)
    whenConfig('aDuration, Configuration.aDuration)
    whenConfig('aInt, Configuration.aInt)
    whenConfig('aLong, Configuration.aLong)
    whenConfig('aString, Configuration.aString)

    // mapped values
    whenConfig('aBigDecimal, Configuration.aBigDecimal)
    whenConfig('aBigInt, Configuration.aBigInt)
    whenConfig('aLocalDate, Configuration.aLocalDate)
    whenConfig('aLocalDateTime, Configuration.aLocalDateTime)
    whenConfig('aLocale, Configuration.aLocale)
    whenConfig('aLocalTime, Configuration.aLocalTime)
    whenConfig('aMonthDay, Configuration.aMonthDay)
    whenConfig('aOffsetDateTime, Configuration.aOffsetDateTime)
    whenConfig('aPeriod, Configuration.aPeriod)
    whenConfig('aUuid, Configuration.aUuid)
    whenConfig('aYear, Configuration.aYear)
    whenConfig('aYearMonth, Configuration.aYearMonth)
    whenConfig('aZonedDateTime, Configuration.aZonedDateTime)
    whenConfig('aZoneId, Configuration.aZoneId)
    whenConfig('aFiniteDuration, Configuration.aFiniteDuration)

    // native list values
    whenConfig.booleans('aBooleanList, Configuration.aBooleanList)
    whenConfig.doubles('aDoubleList, Configuration.aDoubleList)
    whenConfig.durations('aDurationList, Configuration.aDurationList)
    whenConfig.ints('aIntList, Configuration.aIntList)
    whenConfig.longs('aLongList, Configuration.aLongList)
    whenConfig.strings('aStringList, Configuration.aStringList)

    // mapped list values
    whenConfig.bigDecimals('aBigDecimalList, Configuration.aBigDecimalList)
    whenConfig.bigInts('aBigIntList, Configuration.aBigIntList)
    whenConfig.localDates('aLocalDateList, Configuration.aLocalDateList)
    whenConfig.localDateTimes('aLocalDateTimeList, Configuration.aLocalDateTimeList)
    whenConfig.locales('aLocaleList, Configuration.aLocaleList)
    whenConfig.localTimes('aLocalTimeList, Configuration.aLocalTimeList)
    whenConfig.monthDays('aMonthDayList, Configuration.aMonthDayList)
    whenConfig.offsetDateTimes('aOffsetDateTimeList, Configuration.aOffsetDateTimeList)
    whenConfig.periods('aPeriodList, Configuration.aPeriodList)
    whenConfig.uuids('aUuidList, Configuration.aUuidList)
    whenConfig.years('aYearList, Configuration.aYearList)
    whenConfig.yearMonths('aYearMonthList, Configuration.aYearMonthList)
    whenConfig.zonedDateTimes('aZonedDateTimeList, Configuration.aZonedDateTimeList)
    whenConfig.zoneIds('aZoneIdList, Configuration.aZoneIdList)
    whenConfig.finiteDurations('aFiniteDurationList, Configuration.aFiniteDurationList)
  }
}
