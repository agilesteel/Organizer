package organizer

import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin._
import EclipseKeys._

object OrganizerBuild extends Build {
	lazy val buildSettings = Seq(
    		organization := "com.github.agilesteel",
    		version := "1.0.1",
    		scalaVersion := "2.9.2"
  	)

  	override lazy val settings = super.settings ++ buildSettings

	lazy val organizer = Project(
		id = "organizer",
		base = file("."),
		settings = Project.defaultSettings ++ Seq(aggregate in run := true),
		aggregate = Seq(core, delivery)
	).configs( AcceptanceTest, IntegrationTestOwn )
      	.settings( inConfig(AcceptanceTest)(Defaults.testSettings) : _*)
      	.settings( inConfig(IntegrationTestOwn)(Defaults.testSettings) : _*)

	lazy val core = Project(
		id = "organizer-core",
		base = file("organizer-core"),
		settings = Project.defaultSettings ++ coreSettings ++ pureScalaProjectSettings
	).configs( AcceptanceTest, IntegrationTestOwn )
      	.settings( inConfig(AcceptanceTest)(Defaults.testSettings) : _*)
      	.settings( inConfig(IntegrationTestOwn)(Defaults.testSettings) : _*)

	lazy val delivery = Project(
		id = "organizer-delivery",
		base = file("organizer-delivery"),
		settings = Project.defaultSettings ++ deliverySettings ++ pureScalaProjectSettings
	) dependsOn core % "test->test;compile->compile"

  	lazy val AcceptanceTest = config("acceptance") extend(Test)

  	lazy val IntegrationTestOwn = config("integration") extend(Test)

	lazy val pureScalaProjectSettings = Seq(
		unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(Seq(_)),

		unmanagedSourceDirectories in Test <<= (scalaSource in Test)(Seq(_))
	)

  	lazy val coreSettings = Seq(
		resolvers += Resolver.jAudioTagger,

		libraryDependencies ++= Seq(Dependency.jAudioTagger, Dependency.scalaTest),

		scalacOptions ++= Seq("-unchecked", "-deprecation"),

		testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "stdout"),

		EclipseKeys.configurations := Set(Compile, Test, AcceptanceTest, IntegrationTestOwn)
   	)

	lazy val deliverySettings = Seq(
		libraryDependencies ++= Seq(Dependency.scalaSwing, Dependency.scalaTest)
	)
}

object Resolver {
	lazy val jAudioTagger = "java.net/.../jaudiotagger"	at	"http://download.java.net/maven/2/org/jaudiotagger"
}

object Dependency {
	lazy val jAudioTagger =	"org.jaudiotagger"	%	"jaudiotagger"		%	"2.0.1"
	lazy val scalaSwing =	"org.scala-lang"	%	"scala-swing"		%	"2.9.2"
	lazy val scalaTest =	"org.scalatest"		%	"scalatest_2.9.2"	%	"latest.release"	
}