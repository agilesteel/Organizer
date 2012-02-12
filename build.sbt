name := "Organizer"

version := "1.1"

resolvers ++= Seq(
	"jaudiotagger repository" at "http://download.java.net/maven/2/org/jaudiotagger",
	"Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "latest.release" % "test",
	"org.jaudiotagger" % "jaudiotagger" % "latest.release" % "compile",
	"org.scalaz" %% "scalaz-core" % "latest.release"
)

scalacOptions ++= Seq(
	"-unchecked",
	 "-deprecation"
)

testOptions in Test ++= Seq(
	Tests.Argument(TestFrameworks.ScalaTest, "-oS")
)

parallelExecution in Test := true