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
import sbt.CrossVersion
import sbt.Provided
import sbt.Test
import sbt.stringToOrganization

object Dependencies extends Dependencies

trait Dependencies {
  object v {
    val jvm = "1.8"
    val scala = "2.12.11"
    val scalatest = "3.2.0"
    val scapegoat = "1.4.4"
  }

  // Resolvers
  val resolvers = Seq(
  )

  // Java
  val commonsCodec      = "commons-codec"               % "commons-codec"           % "1.14"        % Provided
  val commonsLang       = "org.apache.commons"          % "commons-lang3"           % "3.10"        % Provided
  val commonsText       = "org.apache.commons"          % "commons-text"            % "1.8"         % Provided
  val commonsValidator  = "commons-validator"           % "commons-validator"       % "1.6"         % Provided
  val icu4j             = "com.ibm.icu"                 % "icu4j"                   % "67.1"        % Provided
  val jBCrypt           = "de.svenkubiak"               % "jBCrypt"                 % "0.4.1"       % Provided
  val modeshapeCommon   = "org.modeshape"               % "modeshape-common"        % "5.4.1.Final" % Provided
  val typesafeConfig    = "com.typesafe"                % "config"                  % "1.4.0"       % Provided

  // Scala
  val scalaReflect      = "org.scala-lang"              % "scala-reflect"           % v.scala
  val scalaLogging      = "com.typesafe.scala-logging" %% "scala-logging"           % "3.9.2"

  // Test
  val mockitoScala      = "org.mockito"                %% "mockito-scala-scalatest" % "1.14.8"
  val scalatest         = "org.scalatest"              %% "scalatest-freespec"      % v.scalatest
  val scalatestDiagrams = "org.scalatest"              %% "scalatest-diagrams"      % v.scalatest
  val slf4jNop          = "org.slf4j"                   % "slf4j-nop"               % "1.7.30"

  // Compiler plug-ins
  val linter            = "org.psywerx.hairyfotr"      %% "linter"                  % "0.1.17"
  val macrosParadise    = "org.scalamacros"             % "paradise"                % "2.1.1"  cross CrossVersion.full

  val commonDependencies = Seq(
    commonsCodec
  , commonsLang
  , commonsText
  , commonsValidator
  , icu4j
  , jBCrypt
  , modeshapeCommon
  , scalaLogging
  , typesafeConfig
  )

  val testDependencies = Seq(
    mockitoScala
  , scalatest
  , scalatestDiagrams
  , slf4jNop
  ) map (_ % Test)

  val commonsTestDependencies = Seq(
    mockitoScala
  , scalatest
  , scalatestDiagrams
  , typesafeConfig
  )
}
