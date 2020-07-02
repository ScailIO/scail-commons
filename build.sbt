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
val lib = Dependencies
val utf8 = java.nio.charset.StandardCharsets.UTF_8.toString

/*
 * Project definition
 */

lazy val root = project.in(file(".")).settings(
  name := "scail-commons"
, commonSettings
, scaladocPublishing
).aggregate(
  common
, commons
, commonsTest
).enablePlugins(
  GhpagesPlugin
, ScalaUnidocPlugin
)

lazy val common = project.settings(
  description := "Common classes shared across modules"
, commonSettings
)

lazy val commons = project.settings(
  description := "Utility classes and convenience extension methods for commonly used Scala and Java classes"
, commonSettings
, scalaReflect
, macrosParadise
, scalacOptions += "-Ywarn-unused:-patvars"
, unmanagedClasspath in Test ++= (unmanagedResources in Test).value
, libraryDependencies ++= lib.commonDependencies ++ lib.testDependencies
).dependsOn(
  common
, commonsTest % forTests
)

lazy val commonsTest = project.in(file("commons-test")).settings(
  name := "commons-test"
, description := "Convenience utility classes for testing"
, commonSettings
, scalacOptions --= compileScalacOptions
, scalacOptions ++= testScalacOptions
, libraryDependencies ++= lib.commonsTestDependencies
).dependsOn(
  common
)

lazy val commonSettings =
  projectMetadata ++
  projectLayout ++
  scalacConfiguration ++
  scaladocConfiguration ++
  dependencies ++
  sbtOptions ++
  publishing ++
  staticAnalysis ++
  codeCoverage

val forTests = "test->compile"

/*
 * Project metadata
 */

val projectMetadata = Seq(
  organization := "io.github.scailio"
, organizationName := "Scail"
, organizationHomepage := Option(url("http://github.com/ScailIO"))
, homepage := Option(url("http://github.com/ScailIO/scail-commons"))
, apiURL := Option(url("http://scailio.github.io/scail-commons"))
, startYear := Option(2017)
, licenses += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")
, developers := List(
    Developer("marconilanna", "Marconi Lanna", "@marconilanna", url("http://github.com/marconilanna"))
  )
)

/*
 * Project layout
 */

val projectLayout = Seq(
  scalaSource in Compile := baseDirectory.value / "src"
, scalaSource in Test := baseDirectory.value / "test"
, javaSource in Compile := scalaSource.in(Compile).value
, javaSource in Test := scalaSource.in(Test).value
, resourceDirectory in Compile := scalaSource.in(Compile).value / "resources"
, resourceDirectory in Test := scalaSource.in(Test).value / "resources"
, sourcesInBase := false
)

/*
 * scalac configuration
 */

val coreScalacOptions = Seq(
  "-encoding", utf8 // Specify character encoding used by source files
, "-target:jvm-" + lib.v.jvm // Target platform for object files
, "-Xexperimental" // Enable experimental extensions
, "-Xfuture" // Turn on future language features
)

val commonScalacOptions = Seq(
  "-deprecation" // Emit warning and location for usages of deprecated APIs
, "-feature" // Emit warning and location for usages of features that should be imported explicitly
, "-g:vars" // Set level of generated debugging info: none, source, line, vars, notailcalls
, "-opt:l:inline" // Enable optimizations (see list below)
, "-opt-inline-from:**" // Classfile names from which to allow inlining
, "-opt-warnings:at-inline-failed" // Enable optimizer warnings: detailed warning for each @inline method call that could not be inlined
, "-unchecked" // Enable additional warnings where generated code depends on assumptions
, "-Xfatal-warnings" // Fail the compilation if there are any warnings
, "-Xlint:_" // Enable or disable specific warnings (see list below)
, "-Xlog-free-terms" // Print a message when reification creates a free term
, "-Xlog-free-types" // Print a message when reification resorts to generating a free type
, "-Xlog-reflective-calls" // Print a message when a reflective method call is generated
, "-Xstrict-inference" // Don't infer known-unsound types
, "-Ybackend-parallelism", "8" // Maximum worker threads for backend
, "-Ybackend-worker-queue", "8" // Backend threads worker queue
, "-Ycache-macro-class-loader:last-modified" // Policy for caching class loaders for macros that are dynamically loaded
, "-Ycache-plugin-class-loader:last-modified" // Policy for caching class loaders for compiler plugins that are dynamically loaded
, "-Yno-adapted-args" // Do not adapt an argument list to match the receiver
, "-Ywarn-dead-code" // Warn when dead code is identified
, "-Ywarn-extra-implicit" // Warn when more than one implicit parameter section is defined
, "-Ywarn-macros:before" // Enable lint warnings on macro expansions (see list below)
, "-Ywarn-numeric-widen" // Warn when numerics are widened
, "-Ywarn-unused:_" // Enable or disable specific unused warnings (see list below)
)

val compileScalacOptions = Seq(
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused
)

val testScalacOptions = Seq(
  "-Xcheckinit" // Wrap field accessors to throw an exception on uninitialized access
)

val consoleScalacOptions = Seq(
  "-language:_" // Enable or disable language features (see list below)
, "-nowarn" // Generate no warnings
)

val scalacConfiguration = Seq(
  scalaVersion := lib.v.scala
, scalacOptions ++= coreScalacOptions ++ commonScalacOptions ++ compileScalacOptions
, scalacOptions in (Test, compile) ++= testScalacOptions
, scalacOptions in (Test, compile) --= compileScalacOptions
, scalacOptions in (Compile, console) := coreScalacOptions ++ consoleScalacOptions
, scalacOptions in (Test, console) := scalacOptions.in(Compile, console).value
, compileOrder := CompileOrder.JavaThenScala
)

/*
 * Scaladoc configuration
 */

def docSourceUrl(version: String) = {
  s"http://github.com/ScailIO/scail-commons/blob/${version}€{FILE_PATH_EXT}#L€{FILE_LINE}"
}

val scaladocConfiguration = Seq(
  autoAPIMappings := true
, apiMappings ++= (
    scalaInstance.value.libraryJars.filter { file =>
      file.getName.startsWith("scala-library") && file.getName.endsWith(".jar")
    }.map {
      _ -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/")
    }.toMap
  )
, scalacOptions in (Compile, doc) := coreScalacOptions ++ Seq(
    "-author" // Include authors
  , "-doc-source-url", docSourceUrl("v" + version.value) // A URL pattern used to link to the source file
  , "-doc-title", "Scail Commons" // The overall name of the Scaladoc site
  , "-doc-version", version.value // An optional version number, to be appended to the title
  , "-groups" // Group similar functions together (based on the @group annotation)
  , "-implicits" // Document members inherited by implicit conversions
  , "-sourcepath", baseDirectory.in(ThisBuild).value.toString // To obtain a relative path for €{FILE_PATH_EXT} instead of an absolute one
  )
, scalacOptions in (Test, doc) := scalacOptions.in(Compile, doc).value
)

/*
 * Scaladoc publishing (Scala Unidoc, sbt-site, GitHub Pages)
 */

val scaladocPublishing = Seq(
  addMappingsToSiteDir(mappings.in(ScalaUnidoc, packageDoc), siteSubdirName.in(ScalaUnidoc))
, ghpagesNoJekyll := true
, git.remoteRepo := "git@github.com:ScailIO/scail-commons.git"
, siteSubdirName in ScalaUnidoc := "/"
)

/*
 * Macros
 */

val scalaReflect = libraryDependencies += lib.scalaReflect

val macrosParadise = addCompilerPlugin(lib.macrosParadise)

/*
 * Managed dependencies
 */

val dependencies = Seq(
  resolvers ++= lib.resolvers
)

/*
 * sbt options
 */

val consoleDefinitions = """
import
  scala.annotation.{switch, tailrec}
, scala.beans.{BeanProperty, BooleanBeanProperty}
, scala.collection.JavaConverters._
, scala.collection.{breakOut, mutable}
, scala.concurrent.{Await, ExecutionContext, Future}
, scala.concurrent.ExecutionContext.Implicits.global
, scala.concurrent.duration._
, scala.language.experimental.macros
, scala.math._
, scala.reflect.macros.{blackbox, whitebox}
, scala.reflect.runtime.{currentMirror => mirror}
, scala.reflect.runtime.universe._
, scala.tools.reflect.ToolBox
, scala.util.{Failure, Random, Success, Try}
, scala.util.control.NonFatal
, java.io._
, java.net._
, java.nio.file._
, java.time.{Duration => jDuration, _}
, java.util.{Date, Locale, UUID}
, java.util.regex.{Matcher, Pattern}
, System.{currentTimeMillis => now, nanoTime}

val toolbox = mirror.mkToolBox()

import toolbox.{PATTERNmode, TERMmode, TYPEmode}

def time[T](f: => T): T = {
  val start = now
  try f finally {
    println("Elapsed: " + (now - start)/1000.0 + " s")
  }
}

def desugar[T](expr: => T): Unit = macro desugarImpl[T]

def desugarImpl[T](c: blackbox.Context)(expr: c.Expr[T]) = {
  import c.universe._, scala.io.AnsiColor.{BOLD, GREEN, RESET}

  val exp = showCode(expr.tree)
  val typ = expr.actualType.toString takeWhile '('.!=

  println(s"$exp: $BOLD$GREEN$typ$RESET")

  q"()"
}
"""

cleanKeepFiles += target.in(LocalRootProject).value / ".history"

turbo in ThisBuild := true

val sbtOptions = Seq(
  // Statements executed when starting the Scala REPL (sbt's `console` task)
  initialCommands += consoleDefinitions
  // Improved dependency management
, updateOptions := updateOptions.value.withCachedResolution(true)
  // Clean locally cached project artifacts
, publishLocal := publishLocal
    .dependsOn(cleanCache.toTask(""))
    .dependsOn(cleanLocal.toTask(""))
    .value
, credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")
  // Share history among all projects instead of using a different history for each project
, historyPath := Option(target.in(LocalRootProject).value / ".history")
, cleanKeepFiles := cleanKeepFiles.value filterNot { file =>
    file.getPath.endsWith(".history")
  }
, fork in run := true
, classLoaderLayeringStrategy in Compile := ClassLoaderLayeringStrategy.AllLibraryJars
, classLoaderLayeringStrategy in Test := ClassLoaderLayeringStrategy.AllLibraryJars
, trapExit := false
, connectInput := true
, outputStrategy := Option(StdoutOutput)
, logLevel in Global := { if (insideCI.value) Level.Error else Level.Info }
, logBuffered in Test := false
, onChangedBuildSource in Global := WarnOnSourceChanges
, showSuccess := true
, showTiming := true
  // ScalaTest configuration
, testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest
    // F: show full stack traces
    // S: show short stack traces
    // D: show duration for each test
    // I: print "reminders" of failed and canceled tests at the end of the summary,
    //    eliminating the need to scroll and search to find failed or canceled tests.
    //    replace with G (or T) to show reminders with full (or short) stack traces
    // K: exclude canceled tests from reminder
  , "-oDI"
    // Periodic notification of slowpokes (tests that have been running longer than 30s)
  , "-W", "30", "30"
  )
  // Enable colors in Scala console (2.11.4+)
, initialize ~= { _ =>
    val ansi = System.getProperty("sbt.log.noformat", "false") != "true"
    if (ansi) System.setProperty("scala.color", "true")
  }
  // Draw a separator between triggered runs (e.g, ~test)
, watchTriggeredMessage := { (count, _, _) =>
  val msg = if (count > 1) {
    val nl = System.lineSeparator * 2
    nl + "#" * 72 + nl
  } else ""
  Option(msg)
}
, shellPrompt := { state =>
    import scala.Console.{BLUE, BOLD, RESET}
    s"$BLUE$BOLD${name.value}$RESET $BOLD\u25b6$RESET "
  }
)

addCommandAlias("cd", "project")
addCommandAlias("ls", "projects")
addCommandAlias("cr", ";clean ;reload")
addCommandAlias("cru", ";clean ;reload ;test:update")
addCommandAlias("du", "dependencyUpdates")
addCommandAlias("rdu", ";reload ;dependencyUpdates")
addCommandAlias("ru", ";reload ;test:update")
addCommandAlias("tc", "test:compile")
addCommandAlias("testCoverage", ";clean ;coverageOn ;test ;coverageAggregate ;coverageOff")

// Uncomment to enable offline mode
//offline in ThisBuild := true

/*
 * Publishing settings
 */

import xerial.sbt.Sonatype.GitHubHosting
val publishing = Seq(
  scmInfo := Option(ScmInfo(
    url("http://github.com/ScailIO/scail-commons"),
    "scm:git@github.com:ScailIO/scail-commons.git"
  ))
, pomIncludeRepository := { _ => false }
, publishTo := sonatypePublishTo.value
, publishMavenStyle := true
)

/*
 * Scalastyle: http://www.scalastyle.org/
 */

val scalastyleConfiguration = Seq(
  scalastyleConfig := baseDirectory.in(LocalRootProject).value / "project" / "scalastyle-config.xml"
, scalastyleConfig in Test := baseDirectory.in(LocalRootProject).value / "project" / "scalastyle-test-config.xml"
, scalastyleFailOnError := true
, test in Test := test.in(Test)
    .dependsOn(scalastyle.in(Test).toTask(""))
    .dependsOn(scalastyle.in(Compile).toTask(""))
    .value
)

/*
 * WartRemover: http://github.com/wartremover/wartremover
 */

val wartremoverConfiguration = Seq(
  wartremoverErrors ++= Seq(
    Wart.Any
  , Wart.AnyVal
  , Wart.ArrayEquals
  , Wart.AsInstanceOf
  , Wart.EitherProjectionPartial
  , Wart.ExplicitImplicitTypes
  , Wart.FinalCaseClass
  , Wart.IsInstanceOf
  , Wart.JavaConversions
  , Wart.JavaSerializable
  , Wart.LeakingSealed
  , Wart.Nothing
  , Wart.Null
  , Wart.Option2Iterable
  , Wart.OptionPartial
  , Wart.Product
  , Wart.Return
  , Wart.Serializable
  , Wart.Throw
  , Wart.TraversableOps
  , Wart.TryPartial
  , Wart.Var
  , ContribWart.ExposedTuples
  , ContribWart.OldTime
  , ContribWart.RefinedClasstag
  , ContribWart.SealedCaseClass
  , ContribWart.SomeApply
  , ExtraWart.EnumerationPartial
  , ExtraWart.FutureObject
  , ExtraWart.GenMapLikePartial
  , ExtraWart.GenTraversableLikeOps
  , ExtraWart.GenTraversableOnceOps
  , ExtraWart.ScalaGlobalExecutionContext
  , ExtraWart.StringOpsPartial
  , ExtraWart.ThrowablePartial
  , ExtraWart.TraversableOnceOps
  )
)

/*
 * Scapegoat: http://github.com/sksamuel/scapegoat
 */

scapegoatVersion in ThisBuild := lib.v.scapegoat

val scapegoatConfiguration = Seq(
  scapegoatDisabledInspections := Seq.empty
, scapegoatIgnoredFiles := Seq.empty
, scapegoatReports := Seq("none")
, scalacOptions in Scapegoat += "-P:scapegoat:overrideLevels" +
    ":ArrayEquals=Error" +
    ":ArraysToString=Error" +
    ":AsInstanceOf=Error" +
    ":AvoidOperatorOverload=Error" +
    ":AvoidSizeEqualsZero=Error" +
    ":AvoidSizeNotEqualsZero=Error" +
    ":AvoidToMinusOne=Error" +
    ":BigDecimalDoubleConstructor=Error" +
    ":BigDecimalScaleWithoutRoundingMode=Error" +
    ":BoundedByFinalType=Error" +
    ":BrokenOddness=Error" +
    ":CatchException=Error" +
    ":CatchFatal=Error" +
    ":CatchThrowable=Error" +
    ":ClassNames=Error" +
    ":CollectionIndexOnNonIndexedSeq=Error" +
    ":CollectionNamingConfusion=Error" +
    ":CollectionNegativeIndex=Error" +
    ":CollectionPromotionToAny=Error" +
    ":ComparisonToEmptyList=Error" +
    ":ComparisonToEmptySet=Error" +
    ":ComparisonWithSelf=Error" +
    ":ConstantIf=Error" +
    ":DivideByOne=Error" +
    ":DoubleNegation=Error" +
    ":DuplicateImport=Error" +
    ":DuplicateMapKey=Error" +
    ":DuplicateSetValue=Error" +
    ":EmptyCaseClass=Error" +
    ":EmptyFor=Error" +
    ":EmptyIfBlock=Error" +
    ":EmptyMethod=Error" +
    ":EmptySynchronizedBlock=Error" +
    ":EmptyTryBlock=Error" +
    ":EmptyWhileBlock=Error" +
    ":ExistsSimplifiableToContains=Error" +
    ":FilterDotHead=Error" +
    ":FilterDotHeadOption=Error" +
    ":FilterDotIsEmpty=Error" +
    ":FilterDotSize=Error" +
    ":FilterOptionAndGet=Error" +
    ":FinalizerWithoutSuper=Error" +
    ":FinalModifierOnCaseClass=Error" +
    ":FindAndNotEqualsNoneReplaceWithExists=Error" +
    ":FindDotIsDefined=Error" +
    ":InvalidRegex=Error" +
    ":IsInstanceOf=Error" +
    ":JavaConversionsUse=Error" +
    ":ListAppend=Error" +
    ":ListSize=Error" +
    ":LooksLikeInterpolatedString=Error" +
    ":MaxParameters=Error" +
    ":MethodNames=Error" +
    ":MethodReturningAny=Error" +
    ":ModOne=Error" +
    ":NegationIsEmpty=Error" +
    ":NegationNonEmpty=Error" +
    ":NoOpOverride=Error" +
    ":NullAssignment=Error" +
    ":NullParameter=Error" +
    ":ObjectNames=Error" +
    ":ParameterlessMethodReturnsUnit=Error" +
    ":PartialFunctionInsteadOfMatch=Error" +
    ":PointlessTypeBounds=Error" +
    ":PreferMapEmpty=Error" +
    ":PreferSeqEmpty=Error" +
    ":PreferSetEmpty=Error" +
    ":ProductWithSerializableInferred=Error" +
    ":PublicFinalizer=Error" +
    ":RedundantFinalizer=Error" +
    ":RedundantFinalModifierOnMethod=Error" +
    ":RedundantFinalModifierOnVar=Error" +
    ":RepeatedCaseBody=Error" +
    ":ReverseFunc=Error" +
    ":ReverseTailReverse=Error" +
    ":ReverseTakeReverse=Error" +
    ":SimplifyBooleanExpression=Error" +
    ":SubstringZero=Error" +
    ":SuspiciousMatchOnClassObject=Error" +
    ":SwallowedException=Error" +
    ":SwapSortFilter=Error" +
    ":TypeShadowing=Error" +
    ":UnnecessaryIf=Error" +
    ":UnnecessaryReturnUse=Error" +
    ":UnnecessaryToInt=Error" +
    ":UnnecessaryToString=Error" +
    ":UnreachableCatch=Error" +
    ":UnusedMethodParameter=Error" +
    ":UseCbrt=Error" +
    ":UseExpM1=Error" +
    ":UseLog10=Error" +
    ":UseLog1P=Error" +
    ":UseSqrt=Error" +
    ":VarClosure=Error" +
    ":VarCouldBeVal=Error" +
    ":WhileTrue=Error" +
    ":ZeroNumerator=Error"
, test in Test := test.in(Test)
    .dependsOn(scapegoat.in(Test))
    .dependsOn(scapegoat.in(Compile))
    .value
)

/*
 * Linter: http://github.com/HairyFotr/linter
 */

val linterConfiguration = Seq(
  addCompilerPlugin(lib.linter)
, scalacOptions += "-P:linter:enable-only:" +
    "AssigningOptionToNull+" +
    "AvoidOptionCollectionSize+" +
    "AvoidOptionMethod+" +
    "AvoidOptionStringSize+" +
    "BigDecimalNumberFormat+" +
    "BigDecimalPrecisionLoss+" +
    "CloseSourceFile+" +
    "ContainsTypeMismatch+" +
    "DecomposingEmptyCollection+" +
    "DivideByOne+" +
    "DivideByZero+" +
    "DuplicateIfBranches+" +
    "DuplicateKeyInMap+" +
    "EmptyStringInterpolator+" +
    "FilterFirstThenSort+" +
    "FloatingPointNumericRange+" +
    "FuncFirstThenMap+" +
    "IdenticalCaseBodies+" +
    "IdenticalCaseConditions+" +
    "IdenticalIfCondition+" +
    "IdenticalIfElseCondition+" +
    "IdenticalStatements+" +
    "IfDoWhile+" +
    "IndexingWithNegativeNumber+" +
    "InefficientUseOfListSize+" +
    "IntDivisionAssignedToFloat+" +
    "InvalidParamToRandomNextInt+" +
    "InvalidStringConversion+" +
    "InvalidStringFormat+" +
    "InvariantCondition+" +
    "InvariantExtrema+" +
    "InvariantReturn+" +
    "JavaConverters+" +
    "LikelyIndexOutOfBounds+" +
    "MalformedSwap+" +
    "MergeMaps+" +
    "MergeNestedIfs+" +
    "ModuloByOne+" +
    "NumberInstanceOf+" +
    "OnceEvaluatedStatementsInBlockReturningFunction+" +
    "OperationAlwaysProducesZero+" +
    "OptionOfOption+" +
    "PassPartialFunctionDirectly+" +
    "PatternMatchConstant+" +
    "PossibleLossOfPrecision+" +
    "PreferIfToBooleanMatch+" +
    "ProducesEmptyCollection+" +
    "ReflexiveAssignment+" +
    "ReflexiveComparison+" +
    "RegexWarning+" +
    "StringMultiplicationByNonPositive+" +
    "SuspiciousMatches+" +
    "SuspiciousPow+" +
    "TransformNotMap+" +
    "TypeToType+" +
    "UndesirableTypeInference+" +
    "UnextendedSealedTrait+" +
    "UnitImplicitOrdering+" +
    "UnlikelyEquality+" +
    "UnlikelyToString+" +
    "UnnecessaryMethodCall+" +
    "UnnecessaryReturn+" +
    "UnnecessaryStringIsEmpty+" +
    "UnnecessaryStringNonEmpty+" +
    "UnsafeAbs+" +
    "UnthrownException+" +
    "UnusedForLoopIteratorValue+" +
    "UnusedParameter+" +
    "UseAbsNotSqrtSquare+" +
    "UseCbrt+" +
    "UseConditionDirectly+" +
    "UseContainsNotExistsEquals+" +
    "UseCountNotFilterLength+" +
    "UseExistsNotCountCompare+" +
    "UseExistsNotFilterIsEmpty+" +
    "UseExistsNotFindIsDefined+" +
    "UseExp+" +
    "UseExpm1+" +
    "UseFilterNotFlatMap+" +
    "UseFindNotFilterHead+" +
    "UseFlattenNotFilterOption+" +
    "UseFuncNotFold+" +
    "UseFuncNotReduce+" +
    "UseFuncNotReverse+" +
    "UseGetOrElseNotPatMatch+" +
    "UseGetOrElseOnOption+" +
    "UseHeadNotApply+" +
    "UseHeadOptionNotIf+" +
    "UseHypot+" +
    "UseIfExpression+" +
    "UseInitNotReverseTailReverse+" +
    "UseIsNanNotNanComparison+" +
    "UseIsNanNotSelfComparison+" +
    "UseLastNotApply+" +
    "UseLastNotReverseHead+" +
    "UseLastOptionNotIf+" +
    "UseLog10+" +
    "UseLog1p+" +
    "UseMapNotFlatMap+" +
    "UseMinOrMaxNotSort+" +
    "UseOptionExistsNotPatMatch+" +
    "UseOptionFlatMapNotPatMatch+" +
    "UseOptionFlattenNotPatMatch+" +
    "UseOptionForallNotPatMatch+" +
    "UseOptionForeachNotPatMatch+" +
    "UseOptionGetOrElse+" +
    "UseOptionIsDefinedNotPatMatch+" +
    "UseOptionIsEmptyNotPatMatch+" +
    "UseOptionMapNotPatMatch+" +
    "UseOptionOrNull+" +
    "UseOrElseNotPatMatch+" +
    "UseQuantifierFuncNotFold+" +
    "UseSignum+" +
    "UseSqrt+" +
    "UseTakeRightNotReverseTakeReverse+" +
    "UseUntilNotToMinusOne+" +
    "UseZipWithIndexNotZipIndices+" +
    "VariableAssignedUnusedValue+" +
    "WrapNullWithOption+" +
    "YodaConditions+" +
    "ZeroDivideBy"
)

val staticAnalysis =
  scalastyleConfiguration ++
  wartremoverConfiguration ++
  scapegoatConfiguration ++
  linterConfiguration

/*
 * scoverage: http://github.com/scoverage/sbt-scoverage
 */

val codeCoverage = Seq(
  coverageMinimum := 90
, coverageFailOnMinimum := true
, coverageOutputCobertura := true
, coverageOutputHTML := true
, coverageOutputXML := false
)
