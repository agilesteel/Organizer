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
		settings = Project.defaultSettings,
		aggregate = Seq(core, delivery)
	).configs( AcceptanceTest, IntegrationTestOwn )
      	.settings( inConfig(AcceptanceTest)(Defaults.testSettings) : _*)
      	.settings( inConfig(IntegrationTestOwn)(Defaults.testSettings) : _*)
      	.settings( libraryDependencies += scalaTest)

	lazy val core = Project(
		id = "organizer-core",
		base = file("organizer-core"),
		settings = Project.defaultSettings ++ coreSettings
	).configs( AcceptanceTest, IntegrationTestOwn )
      	.settings( inConfig(AcceptanceTest)(Defaults.testSettings) : _*)
      	.settings( inConfig(IntegrationTestOwn)(Defaults.testSettings) : _*)
      	.settings( libraryDependencies += scalaTest)

  	lazy val AcceptanceTest = config("acceptance") extend(Test)

  	lazy val IntegrationTestOwn = config("integration") extend(Test)

  	lazy val scalaTest = "org.scalatest" %% "scalatest" % "latest.release"

	lazy val delivery = Project(
		id = "organizer-delivery",
		base = file("organizer-delivery"),
		settings = Project.defaultSettings ++ deliverySettings
	) dependsOn core % "test->test;compile->compile"

  	lazy val coreSettings = Seq(
		resolvers ++= Seq(
			"jaudiotagger repository" at "http://download.java.net/maven/2/org/jaudiotagger",
			"Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
		),

		libraryDependencies ++= Seq(
			"org.jaudiotagger" % "jaudiotagger" % "latest.release",
			"org.scalatest" %% "scalatest" % "latest.release" % "test"
		),

		scalacOptions ++= Seq(
			"-unchecked",
	 		"-deprecation"
		),

		testOptions in Test ++= Seq(
	  		Tests.Argument(TestFrameworks.ScalaTest, "stdout")
     		),

     		EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
    	
		EclipseKeys.configurations := Set(Compile, Test, AcceptanceTest, IntegrationTestOwn)
   	)

	lazy val deliverySettings = Seq(
		libraryDependencies ++= Seq(
			"org.scala-lang" % "scala-swing" % "2.9.2",
			"org.scalatest" %% "scalatest" % "latest.release" % "test"
		)
	)
}