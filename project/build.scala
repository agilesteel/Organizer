import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin._
import EclipseKeys._

object OrganizerBuild extends Build {
  lazy val root =
    Project(id = "Organizer",
            base = file("."),
            settings = organizerSettings)
      .configs( AcceptanceTest, IntegrationTestOwn )
      .settings( inConfig(AcceptanceTest)(Defaults.testSettings) : _*)
      .settings( inConfig(IntegrationTestOwn)(Defaults.testSettings) : _*)
      .settings( libraryDependencies += scalaTest)

  lazy val AcceptanceTest = config("acceptance") extend(Test)

  lazy val IntegrationTestOwn = config("integration") extend(Test)

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "latest.release"

  lazy val organizerSettings = Project.defaultSettings ++ Seq(
	name := "Organizer",

	version := "1.1",

	scalaVersion := "2.9.2",

	resolvers ++= Seq(
		"jaudiotagger repository" at "http://download.java.net/maven/2/org/jaudiotagger",
		"Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
	),

	libraryDependencies ++= Seq(
		"org.jaudiotagger" % "jaudiotagger" % "latest.release",
		"org.scalaz" %% "scalaz-core" % "latest.release"
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
}