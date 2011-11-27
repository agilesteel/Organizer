name := "Organizer"

version := "1.0"

resolvers ++= Seq(
	"jaudiotagger repository" at "http://download.java.net/maven/2/org/jaudiotagger"
)

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "latest.release" % "test",
	"org.jaudiotagger" % "jaudiotagger" % "latest.release" % "compile",
	"org.scalaz" %% "scalaz-core" % "latest.release",
	"org.scala-lang" % "scala-swing" % "2.9.1"
)

scalacOptions ++= Seq(
	"-unchecked",
	 "-deprecation"
)

testOptions in Test ++= Seq(
	Tests.Argument(TestFrameworks.ScalaTest, "-oS")
)