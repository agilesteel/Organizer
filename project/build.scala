import sbt._
import Keys._

object OrganizerBuild extends Build {
  lazy val root =
    Project("root", file("."))
      .configs( AcceptanceTest, IntegrationTest )
      .settings( inConfig(AcceptanceTest)(Defaults.testSettings) : _*)
      .settings( inConfig(IntegrationTest)(Defaults.testSettings) : _*)

  lazy val AcceptanceTest = config("acceptance") extend(Test)
  lazy val IntegrationTest = config("integration") extend(Test)
}