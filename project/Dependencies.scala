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
import sbt.CrossVersion
import sbt.Provided
import sbt.Test
import sbt.stringToOrganization

object Dependencies extends Dependencies

trait Dependencies {
  object v {
    val jvm = "1.8"
    val scala = "2.12.9"
    val scapegoat = "1.3.10"
  }

  // Resolvers
  val resolvers = Seq(
  )

  // Java
  val commonsLang      = "org.apache.commons"          % "commons-lang3"           % "3.9"         % Provided
  val icu4j            = "com.ibm.icu"                 % "icu4j"                   % "64.2"        % Provided
  val modeshapeCommon  = "org.modeshape"               % "modeshape-common"        % "5.4.1.Final" % Provided
  val typesafeConfig   = "com.typesafe"                % "config"                  % "1.3.4"       % Provided

  // Scala
  val scalaReflect     = "org.scala-lang"              % "scala-reflect"           % v.scala
  val scalaLogging     = "com.typesafe.scala-logging" %% "scala-logging"           % "3.9.2"

  // Test
  val mockitoScala     = "org.mockito"                %% "mockito-scala-scalatest" % "1.5.14"
  val scalatest        = "org.scalatest"              %% "scalatest"               % "3.0.8"
  val slf4jNop         = "org.slf4j"                   % "slf4j-nop"               % "1.7.28"

  // Compiler plug-ins
  val linter           = "org.psywerx.hairyfotr"      %% "linter"                  % "0.1.17"
  val macrosParadise   = "org.scalamacros"             % "paradise"                % "2.1.1"  cross CrossVersion.full

  val commonDependencies = Seq(
    commonsLang
  , icu4j
  , modeshapeCommon
  , scalaLogging
  , typesafeConfig
  )

  val testDependencies = Seq(
    mockitoScala
  , scalatest
  , slf4jNop
  ) map (_ % Test)

  val commonsTestDependencies = Seq(
    mockitoScala
  , scalatest
  , typesafeConfig
  )
}
