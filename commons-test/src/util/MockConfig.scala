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

import scail.commons.Mocking

import com.typesafe.config.{Config => TypesafeConfig}

import org.mockito.Mockito.when

import scala.collection.JavaConverters.seqAsJavaListConverter
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
import java.util.List
import java.util.Locale
import java.util.UUID

// scalastyle:off number.of.methods
trait MockConfig extends Mocking {
  val typesafeConfig = mockReturnsSelf[TypesafeConfig]

  object whenConfig {
    private def asString[A](key: Key, value: A): Unit = {
      addKey(key)
      typesafeConfig.getString(key.name) returns value.toString
    }

    private def asStringList[A](key: Key, value: Seq[A]): Unit = {
      addKey(key)
      typesafeConfig.getStringList(key.name) returns value.map(_.toString).asJava
    }

    private def toDuration(value: FiniteDuration) = Duration.ofNanos(value.toNanos)

    def addKey(key: Key): Unit = typesafeConfig.hasPath(key.name) returns true

    // native values
    def apply(key: Key, value: Boolean): Unit = {
      addKey(key)
      typesafeConfig.getBoolean(key.name) returns value
    }

    def apply(key: Key, value: Double): Unit = {
      addKey(key)
      typesafeConfig.getDouble(key.name) returns value
    }

    def apply(key: Key, value: Duration): Unit = {
      addKey(key)
      typesafeConfig.getDuration(key.name) returns value
    }

    def apply(key: Key, value: Int): Unit = {
      addKey(key)
      typesafeConfig.getInt(key.name) returns value
    }

    def apply(key: Key, value: Long): Unit = {
      addKey(key)
      typesafeConfig.getLong(key.name) returns value
    }

    def apply(key: Key, value: String): Unit = asString(key, value)

    // native list values
    def booleans(key: Key, value: Seq[Boolean]): Unit = {
      addKey(key)
      typesafeConfig.getBooleanList(key.name) returns value.map(boolean2Boolean).asJava
    }

    def doubles(key: Key, value: Seq[Double]): Unit = {
      addKey(key)
      typesafeConfig.getDoubleList(key.name) returns value.map(double2Double).asJava
    }

    def durations(key: Key, value: Seq[Duration]): Unit = {
      addKey(key)
      typesafeConfig.getDurationList(key.name) returns value.asJava
    }

    def ints(key: Key, value: Seq[Int]): Unit = {
      addKey(key)
      typesafeConfig.getIntList(key.name) returns value.map(int2Integer).asJava
    }

    def longs(key: Key, value: Seq[Long]): Unit = {
      addKey(key)
      typesafeConfig.getLongList(key.name) returns value.map(long2Long).asJava
    }

    def strings(key: Key, value: Seq[String]): Unit = asStringList(key, value)

    // mapped values
    def apply(key: Key, value: BigDecimal): Unit = asString(key, value)
    def apply(key: Key, value: BigInt): Unit = asString(key, value)
    def apply(key: Key, value: LocalDate): Unit = asString(key, value)
    def apply(key: Key, value: LocalDateTime): Unit = asString(key, value)
    def apply(key: Key, value: Locale): Unit = asString(key, value)
    def apply(key: Key, value: LocalTime): Unit = asString(key, value)
    def apply(key: Key, value: MonthDay): Unit = asString(key, value)
    def apply(key: Key, value: OffsetDateTime): Unit = asString(key, value)
    def apply(key: Key, value: Period): Unit = asString(key, value)
    def apply(key: Key, value: UUID): Unit = asString(key, value)
    def apply(key: Key, value: Year): Unit = asString(key, value)
    def apply(key: Key, value: YearMonth): Unit = asString(key, value)
    def apply(key: Key, value: ZonedDateTime): Unit = asString(key, value)
    def apply(key: Key, value: ZoneId): Unit = asString(key, value)

    def apply(key: Key, value: FiniteDuration): Unit = {
      addKey(key)
      typesafeConfig.getDuration(key.name) returns toDuration(value)
    }

    // mapped list values
    def bigDecimals(key: Key, value: Seq[BigDecimal]): Unit = asStringList(key, value)
    def bigInts(key: Key, value: Seq[BigInt]): Unit = asStringList(key, value)
    def localDates(key: Key, value: Seq[LocalDate]): Unit = asStringList(key, value)
    def localDateTimes(key: Key, value: Seq[LocalDateTime]): Unit = asStringList(key, value)
    def locales(key: Key, value: Seq[Locale]): Unit = asStringList(key, value)
    def localTimes(key: Key, value: Seq[LocalTime]): Unit = asStringList(key, value)
    def monthDays(key: Key, value: Seq[MonthDay]): Unit = asStringList(key, value)
    def offsetDateTimes(key: Key, value: Seq[OffsetDateTime]): Unit = asStringList(key, value)
    def periods(key: Key, value: Seq[Period]): Unit = asStringList(key, value)
    def uuids(key: Key, value: Seq[UUID]): Unit = asStringList(key, value)
    def years(key: Key, value: Seq[Year]): Unit = asStringList(key, value)
    def yearMonths(key: Key, value: Seq[YearMonth]): Unit = asStringList(key, value)
    def zonedDateTimes(key: Key, value: Seq[ZonedDateTime]): Unit = asStringList(key, value)
    def zoneIds(key: Key, value: Seq[ZoneId]): Unit = asStringList(key, value)

    def finiteDurations(key: Key, value: Seq[FiniteDuration]): Unit = {
      addKey(key)
      typesafeConfig.getDurationList(key.name) returns value.map(toDuration).asJava
    }

    def apply(key: Key, value: Seq[TypesafeConfig]): Unit = {
      addKey(key)
      when[List[_ <: TypesafeConfig]](typesafeConfig.getConfigList(key.name))
        .thenReturn(value.asJava)
    }
  }
}
