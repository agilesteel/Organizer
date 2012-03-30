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
     EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
     EclipseKeys.configurations := Set(Compile, Test, AcceptanceTest, IntegrationTestOwn),
     testOptions in Test ++= Seq(
	  Tests.Argument(TestFrameworks.ScalaTest, "-oS")
     ))
}