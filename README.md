scail-commons
=============

[![Build Status](https://travis-ci.org/ScailIO/scail-commons.svg)](https://travis-ci.org/ScailIO/scail-commons)
[![Coverage Status](https://coveralls.io/repos/github/ScailIO/scail-commons/badge.svg)](https://coveralls.io/github/ScailIO/scail-commons)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.scailio/commons_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.scailio/commons_2.12)

Scala's utility belt.
Utility classes and convenience extension methods for commonly used Scala and Java classes.

Installation
------------

For Scala 2.12, add the following to `libraryDependencies`:

```scala
"io.github.scailio" %% "commons" % "1.0.0"
```

To use the `Spec`, `Mocking`, and `MockConfig` traits, add:

```scala
"io.github.scailio" %% "commons-test" % "1.0.0" % Test
```

Also see the [transitive dependencies](#transitive-dependencies) requirements below.

API documentation
-----------------

Scaladoc API documentation is available at http://scailio.github.io/scail-commons/scail/commons

Typesafe Config wrapper
-----------------------

Supported types:

* Natively by Typesafe Config:
  * `Boolean`
  * `Double`
  * `Int`
  * `Long`
  * `String`
  * `java.time.Duration`
* Mapped (usually from strings converted using a factory method from the respective class):
  * `scala.concurrent.duration.FiniteDuration`
  * `scala.math.BigDecimal`
  * `scala.math.BigInt`
  * `java.time.LocalDate`
  * `java.time.LocalDateTime`
  * `java.time.LocalTime`
  * `java.time.MonthDay`
  * `java.time.OffsetDateTime`
  * `java.time.Period`
  * `java.time.Year`
  * `java.time.YearMonth`
  * `java.time.ZonedDateTime`
  * `java.time.ZoneId`
  * `java.util.Locale`
  * `java.util.UUID`

```scala
import scail.commons.util.Config
import scail.commons.util.Config.ConfigOps
import com.typesafe.config.{Config => TypesafeConfig}

class MyConfig(underlying: TypesafeConfig) extends Config(underlying) {
  object Db extends Config('db) {
    // Throws an exception at construction time if the setting is missing, as recommended by
    // https://github.com/typesafehub/config#schemas-and-validation
    val url: String = 'url.required // equivalent to config.getString("db.url")
  }

  val a = 'a.required[Boolean]
  val b: Double = 'b.required

  val c = 'c.optional[Duration] // c: Option[Duration]
  val d: Option[Int] = 'd.optional

  val e = 'e orElse 42L // e: Long
  val f = 'f.orBlank // f: String
  val g = 'g.orFalse // g: Boolean
  val h = 'h.orTrue // h: Boolean
  val i = 'i.orZero // i: Int

  val r = 'r.required[Seq[FiniteDuration]]
  val s: Seq[BigDecimal] = 's.required

  val t = 't.optional[Seq[LocalDateTime]] // t: Option[Seq[LocalDateTime]]
  val u: Option[Seq[Year]] = 'u.optional

  val v = 'v.orEmpty[Locale] // v: Seq[Locale]
  val w: Seq[UUID] = 'w.orEmpty

  val x: String = Symbol("a.very-complicated:key_name!").required
}
```

Custom `Reader`s are easily created by mapping on existing readers:

```scala
import scail.commons.util.Config.Reader

case class Age(age: Int)
case class Name(name: String)

implicit val ageReader = Reader.int.map(Age.apply)
implicit val nameReader = Reader.string.map(Name.apply)

val myName: Name = 'myName.required
val myAge = 'myAge.optional[Age]
val friendNames: Seq[Name] = 'friendNames.orEmpty
```

Creating a custom `Reader` for composite data types is also straightforward:

```scala
case class Person(name: Name, age: Age, friends: Seq[Person])

object Person {
  implicit val personReader = Reader { implicit config =>
    Person('name.required, 'age.required, 'friends.required)
  }
}
```

Internationalization
--------------------

`scail.commons.i18n.Messages` provides advanced support for internationalization (i18n)
and localization (l10n) features.

Message files (located in `resources/i18n/`, by default) are written using
[Typesafe Config](https://github.com/typesafehub/config) "HOCON" format.
[HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) fully
supports UTF-8 Unicode and is more flexible and powerful than Java `.properties`.

For instance, it is possible to have a locale such as "en-CA" to automatically
default to another locale, say "en", if a message is missing:
just add the `include "messages.en.conf"` line to the `messages.en-CA.conf` file.

Messages are rendered by [ICU4J](http://icu-project.org/), which provides
comprehensive support for Unicode, globalization, and internationalization.
Compared to Java `MessageFormat`, ICU4J supports
[named and numbered arguments](http://icu-project.org/apiref/icu4j/com/ibm/icu/text/MessageFormat.html),
enhanced [gender](http://icu-project.org/apiref/icu4j/com/ibm/icu/text/SelectFormat.html)
and [plurals](http://icu-project.org/apiref/icu4j/com/ibm/icu/text/PluralFormat.html),
user-friendly apostrophe quoting syntax,
cardinal ('one', 'two') and ordinal numbers ('1st', '2nd', '3rd'),
and [much more](http://site.icu-project.org/home/why-use-icu4j).

### `@i18n` macro annotation

Optionally, one may use the `@i18n` macro annotation for classes and objects.
The `@i18n` macro reads the messages file at compile time and generates typesafe
methods for every localized message.

```scala
@i18n object messages

implicit val defaultLocale = Locale.ENGLISH
val ptBr = Locale.forLanguageTag("pt-BR")

assert(messages.helloWorld == "Hello World!")
assert(messages.helloWorld(ptBr) == "Olá Mundo!")
```

Formatted messages are also supported:

```scala
assert(messages.hello(name = "Scail") == "Hello Scail!")
assert(messages.hello(name = "Scail")(ptBr) == "Olá Scail!")
```

Extension methods
-----------------

Selected examples.
See [Scaladoc](http://scailio.github.io/scail-commons/scail/commons)
and [unit tests](commons/test/ops) for more.

```scala
import scail.commons.ops.AnyOps

assert(1.option == Option(1))

val fut = "a".future // Future.successful("a"))
assert(fut.isCompleted)
assert(fut.value.contains(Success("a")))

// Booleans
import scail.commons.ops.BooleanOps

def abs(i: Int) = (i >= 0) ? i | -i // ternary operator
assert(abs(-123) >= 0)

assert((1 > 0).thenOption(42) == Option(42))
assert((1 < 0).thenOption(42) == None)

// Ordinals
import scail.commons.ops.IntOps

assert(1.ordinalize == "1st")
assert(2.ordinalize == "2nd")
assert(3.ordinalize == "3rd")
assert(4.ordinalize == "4th")

// Option
import scail.commons.ops.OptionOps

val a: Option[Int] = None
val b: Option[Int] = Option(42)
val c: Option[String] = None
val d: Option[Seq[Int]] = None

assert(a.orEmpty == 0)
assert(b.orEmpty == 42)
assert(c.orEmpty == "")
assert(d.orEmpty == Seq.empty)

// Defining custom default values
import scail.commons.ops.DefaultValue

implicit val myClass: DefaultValue[MyClass] = DefaultValue(???)
val e: Option[MyClass] = None
assert(e.orEmpty == ???)

// Collections
import scail.commons.ops.collection.IndexedSeqOps

val col = 1 to 10
val elem = col.randomElement
assert(col.contains(elem))
```

Transitive dependencies
-----------------------

Some modules require the following dependencies:

Module                    | Dependency
------------------------- | ------------------------------------------------------
`Config`                  | `"com.typesafe" % "config" % "1.3.4"`
`IntOps#ordinalize`       | `"org.modeshape" % "modeshape-common" % "5.4.1.Final"`
`Messages`                | `"com.ibm.icu" % "icu4j" % "64.2"`
`ThrowableOps#stackTrace` | `"org.apache.commons" % "commons-lang3" % "3.9"`

License
-------

Copyright 2017-2019 Marconi Lanna

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
