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
package scail.commons.util

import scail.commons.util.Config.UnderlyingReader

import com.typesafe.config.{Config => TypesafeConfig}

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.concurrent.duration.{Duration => sDuration}
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

/**
 * Type class based wrapper for Typesafe Config.
 *
 * Supported types:
 *
 *  - Natively by Typesafe Config:
 *    - `Boolean`
 *    - `Double`
 *    - `Int`
 *    - `Long`
 *    - `String`
 *    - `java.time.Duration`
 *  - Mapped (usually from strings converted using a factory method from the respective class):
 *    - `scala.concurrent.duration.FiniteDuration`
 *    - `scala.math.BigDecimal`
 *    - `scala.math.BigInt`
 *    - `java.time.LocalDate`
 *    - `java.time.LocalDateTime`
 *    - `java.time.LocalTime`
 *    - `java.time.MonthDay`
 *    - `java.time.OffsetDateTime`
 *    - `java.time.Period`
 *    - `java.time.Year`
 *    - `java.time.YearMonth`
 *    - `java.time.ZonedDateTime`
 *    - `java.time.ZoneId`
 *    - `java.util.Locale`
 *    - `java.util.UUID`
 *
 * @example {{{
 * import scail.commons.util.Config
 * import scail.commons.util.Config.ConfigOps
 * import com.typesafe.config.{Config => TypesafeConfig}
 *
 * class MyConfig(underlying: TypesafeConfig) extends Config(underlying) {
 *   object Db extends Config('db) {
 *     // Throws an exception at construction time if the setting is missing, as recommended by
 *     // https://github.com/typesafehub/config#schemas-and-validation
 *     val url: String = 'url.required // equivalent to config.getString("db.url")
 *   }
 *
 *   val a = 'a.required[Boolean]
 *   val b: Double = 'b.required
 *
 *   val c = 'c.optional[Duration] // c: Option[Duration]
 *   val d: Option[Int] = 'd.optional
 *
 *   val e = 'e orElse 42L // e: Long
 *   val f = 'f.orBlank // f: String
 *   val g = 'g.orFalse // g: Boolean
 *   val h = 'h.orTrue // h: Boolean
 *   val i = 'i.orZero // i: Int
 *
 *   val r = 'r.required[Seq[FiniteDuration]]
 *   val s: Seq[BigDecimal] = 's.required
 *
 *   val t = 't.optional[Seq[LocalDateTime]] // t: Option[Seq[LocalDateTime]]
 *   val u: Option[Seq[Year]] = 'u.optional
 *
 *   val v = 'v.orEmpty[Locale] // v: Seq[Locale]
 *   val w: Seq[UUID] = 'w.orEmpty
 *
 *   val x: String = Symbol("a.very-complicated:key_name!").required
 * }
 * }}}
 *
 * Custom `Reader`s are easily created by mapping on existing readers:
 *
 * @example {{{
 * import scail.commons.util.Config.Reader
 *
 * case class Age(age: Int)
 * case class Name(name: String)
 *
 * implicit val ageReader = Reader.int.map(Age.apply)
 * implicit val nameReader = Reader.string.map(Name.apply)
 *
 * val myName: Name = 'myName.required
 * val myAge = 'myAge.optional[Age]
 * val friendNames: Seq[Name] = 'friendNames.orEmpty
 * }}}
 *
 * Creating a custom `Reader` for composite data types is also straightforward:
 *
 * @example {{{
 * case class Person(name: Name, age: Age, friends: Seq[Person])
 *
 * object Person {
 *   implicit val personReader = Reader { implicit config =>
 *     Person('name.required, 'age.required, 'friends.required)
 *   }
 * }
 * }}}
 */
abstract class Config private (private val underlying: TypesafeConfig, path: String) {
  def this(underlying: TypesafeConfig) = this(underlying, "")

  protected def this(section: Key)(implicit parent: Config) = {
    this(parent.underlying, parent.path(section) + ".")
  }

  private def apply[A](reader: UnderlyingReader[A], key: String): A = reader(underlying)(path(key))
  private def has(key: String): Boolean = underlying.hasPath(path(key))

  protected def path(key: Key): String = path + key.name

  protected implicit val self = this
}

object Config {
  private type UnderlyingReader[A] = TypesafeConfig => String => A

  sealed abstract class Reader[+A] { self =>
    def apply(key: String)(implicit config: Config): A

    def map[A2 >: A, B](convert: A2 => B): MappedReader[A2, B] = new MappedReader[A2, B](convert) {
      def apply(key: String)(implicit config: Config): B = convert(self(key))
    }

    private[Config] def toSeq[B](implicit ev: A <:< List[B]): Reader[Seq[B]] = new Reader[Seq[B]] {
      def apply(key: String)(implicit config: Config): Seq[B] = ev(self(key)).asScala
    }

    private[Config] def seqOf[B, C](convert: B => C)(implicit ev: A <:< List[_ <: B])
      : Reader[Seq[C]] = new Reader[Seq[C]] {
      def apply(key: String)(implicit config: Config): Seq[C] = ev(self(key)).asScala map convert
    }
  }

  sealed abstract class MappedReader[B, +A](private[Config] val convert: B => A) extends Reader[A]

  object Reader {
    private type ConfigReader[+A] = MappedReader[Config, A]

    private def toFiniteDuration(value: Duration) = sDuration.fromNanos(value.toNanos)
    private def toLocale(value: String) = Locale.forLanguageTag(value.replace('_', '-'))

    private def underlyingReader[A](reader: UnderlyingReader[A]): Reader[A] = new Reader[A] {
      def apply(key: String)(implicit config: Config): A = config(reader, key)
    }

    def apply[A](convert: Config => A): ConfigReader[A] = new MappedReader[Config, A](convert) {
      def apply(key: String)(implicit config: Config): A = convert(new Config(key) {})
    }

    // native values
    implicit val boolean = underlyingReader(_.getBoolean)
    implicit val double = underlyingReader(_.getDouble)
    implicit val int = underlyingReader(_.getInt)
    implicit val long = underlyingReader(_.getLong)
    implicit val string = underlyingReader(_.getString)
    implicit val duration = underlyingReader(_.getDuration)

    // native list values
    implicit val booleanList = underlyingReader(_.getBooleanList).seqOf(Boolean2boolean)
    implicit val doubleList = underlyingReader(_.getDoubleList).seqOf(Double2double)
    implicit val intList = underlyingReader(_.getIntList).seqOf(Integer2int)
    implicit val longList = underlyingReader(_.getLongList).seqOf(Long2long)
    implicit val stringList = underlyingReader(_.getStringList).toSeq
    implicit val durationList = underlyingReader(_.getDurationList).toSeq

    // mapped values
    implicit val finiteDuration = duration.map(toFiniteDuration)
    implicit val bigDecimal = string.map(BigDecimal.apply(_: String))
    implicit val bigInt = string.map(BigInt.apply(_: String))
    implicit val localDate = string.map(LocalDate.parse)
    implicit val localDateTime = string.map(LocalDateTime.parse)
    implicit val localTime = string.map(LocalTime.parse)
    implicit val monthDay = string.map(MonthDay.parse)
    implicit val offsetDateTime = string.map(OffsetDateTime.parse)
    implicit val period = string.map(Period.parse)
    implicit val year = string.map(Year.parse)
    implicit val yearMonth = string.map(YearMonth.parse)
    implicit val zonedDateTime = string.map(ZonedDateTime.parse)
    implicit val zoneId = string.map(ZoneId.of)
    implicit val locale = string.map(toLocale)
    implicit val uuid = string.map(UUID.fromString)

    // mapped list values
    implicit def listReader[B, A](implicit mapper: MappedReader[B, A], reader: Reader[Seq[B]])
      : Reader[Seq[A]] = reader.map[Seq[B], Seq[A]](_.map(mapper.convert))

    implicit def configListReader[A](implicit mapper: ConfigReader[A]): Reader[Seq[A]] = {
      underlyingReader(_.getConfigList).seqOf { underlying: TypesafeConfig =>
        mapper.convert(new Config(underlying) {})
      }
    }
  }

  implicit class ConfigOps(private val value: Symbol) extends AnyVal {
    private def key = value.name

    def required[A](implicit config: Config, read: Reader[A]): A = read(key)

    def optional[A](implicit config: Config, read: Reader[A]): Option[A] = {
      if (config.has(key)) Option(read(key)) else None
    }

    def orElse[A](default: => A)(implicit config: Config, read: Reader[A]): A = {
      if (config.has(key)) read(key) else default
    }

    // Convenience sorthands
    def orBlank(implicit config: Config): String = orElse("")
    def orFalse(implicit config: Config): Boolean = orElse(false)
    def orTrue (implicit config: Config): Boolean = orElse(true)
    def orZero (implicit config: Config): Int = orElse(0)

    def orEmpty[A](implicit config: Config, read: Reader[Seq[A]]): Seq[A] = orElse(Seq.empty[A])
  }
}
