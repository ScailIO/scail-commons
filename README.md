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
"io.github.scailio" %% "commons" % "1.1.0"
```

To use the `Spec`, `Mocking`, and `MockConfig` traits, add:

```scala
"io.github.scailio" %% "commons-test" % "1.1.0" % Test
```

Also see the [transitive dependencies](#transitive-dependencies) requirements below.

API documentation
-----------------

Scaladoc API documentation is available at http://scailio.github.io/scail-commons/latest/scail/commons/

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

// Maps
import scail.commons.ops.collection.MapOps

val m = Map(1 -> 2, 2 -> 3, 3 -> 5, 4 -> 7)

assert(m.containsEntry(2 -> 3))

assert(m.existsKey(_ > 3))
assert(m.existsValue(_ > 6))

assert(m.filterValues(_ % 2 == 0) == Map(1 -> 2))

assert(m.forallKey(_ < 5))
assert(m.forallValue(_ > 1))

assert(m.mapKeys(2.*).contains(8))

// Traversable
import scail.commons.ops.collection.TraversableOps

val s = Seq(1, 2, 3, 2, 5, 4, 5, 3, 2)
assert(s.duplicates.sorted == Seq(2, 3, 5))

val t = -2 to 5
assert(t.duplicatesBy(_.abs).sorted == Seq(1, 2))

val r = Seq(true, 3.14, -1, "hi", false, 42)
assert(r.filterByType[Int] == Seq(-1, 42))

// Strings
import scail.commons.ops.string.StringOps

assert("  \n\t ".isBlank)

assert("y".isTruthy)
assert("f".isFalsy)

assert(" abc \t def\n\nghi ".normalizeSpace == "abc def ghi")

assert("àéîõüçÃÈÍÖÛÇ".stripAccents == "aeioucAEIOUC")

// Digests
import scail.commons.ops.string.DigestOps

assert("hi".md5 startsWith "49f68")
assert("hi".sha1 startsWith "c22b5")
assert("hi".sha256 startsWith "8f434")

// Escaping and unescaping
import scail.commons.ops.string.EscapeOps

assert("\t".escapeJava == "\\t")
assert("\"".escapeHtml == "&quot;")
assert("&quot;".unescapeHtml == "\"")

// Inflection
import scail.commons.ops.string.InflectorOps

assert("hello_world".humanize == "Hello world")
assert("hello_world".humanizeTitle == "Hello World")
assert("hello_world".lowerCamelCase == "helloWorld")
assert("hello_world".upperCamelCase == "HelloWorld")
assert("helloWorld".underscore == "hello_world")

assert("car".pluralize == "cars")
assert("car".pluralize(1) == "car")
assert("car".pluralize(2) == "cars")
assert("cars".singularize == "car")

// Validation
import scail.commons.ops.string.ValidatorOps

assert("example.com".isValidDomain)
assert("user@example.com".isValidEmail)
assert("http://example.com".isValidUrl)

// BCrypt password encryption
import scail.commons.ops.util.BCryptOps

val password = "123456".bcrypt
assert(password != "123456")
assert("123456".bcryptMatches(password))

// File
import scail.commons.ops.util.FileOps

val f = new File("test.txt")
assert(f.hasExtension("txt"))

// Random
import scail.commons.ops.util.RandomOps
import scala.util.Random

val dice = Random.between(1, 6)
assert(dice >= 1 && dice <= 6)

val r1 = Random.nextAlphanumeric(8) // A-Z, a-z, 0-9
assert(r1.size == 8)
assert(r1.forall(_.isLetterOrDigit))

val r2 = Random.nextAlphabetic(10) // A-Z, a-z
assert(r2.size == 10)
assert(r2.forall(_.isLetter))

// Regex
import scail.commons.ops.util.RegexOps

val regex = "a.*e".r
assert(regex.matches("apple"))
```

Transitive dependencies
-----------------------

Some modules require the following dependencies:

Module                    | Dependency
------------------------- | ------------------------------------------------------
`BCryptOps`               | `"de.svenkubiak" % "jBCrypt" % "0.4.1"`
`Config`                  | `"com.typesafe" % "config" % "1.4.0"`
`DigestOps`               | `"commons-codec" % "commons-codec" % "1.14"`
`EscapeOps`               | `"org.apache.commons" % "commons-text" % "1.8"`
`InflectorOps`            | `"org.modeshape" % "modeshape-common" % "5.4.1.Final"`
`IntOps#ordinalize`       | `"org.modeshape" % "modeshape-common" % "5.4.1.Final"`
`Messages`                | `"com.ibm.icu" % "icu4j" % "67.1"`
`StringOps`               | `"org.apache.commons" % "commons-lang3" % "3.10"`
`StringOps#titleCase`     | `"org.apache.commons" % "commons-text" % "1.8"`
`ThrowableOps#stackTrace` | `"org.apache.commons" % "commons-lang3" % "3.10"`
`ValidatorOps`            | `"commons-validator" % "commons-validator" % "1.6"`

License
-------

Copyright 2017-2020 Marconi Lanna

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
