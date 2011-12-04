name := "Organizer"

version := "1.0"

seq(webSettings: _*)

resolvers ++= Seq(
	"jaudiotagger repository" at "http://download.java.net/maven/2/org/jaudiotagger",
	"Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "latest.release" % "test",
	"org.jaudiotagger" % "jaudiotagger" % "latest.release" % "compile",
	"org.scalaz" %% "scalaz-core" % "latest.release",
	"org.scala-lang" % "scala-swing" % "2.9.1",
  	"net.liftweb" %% "lift-webkit" % "latest.release",
  	"org.eclipse.jetty" % "jetty-webapp" % "latest.release" % "container, test", 
  	"ch.qos.logback" % "logback-classic" % "latest.release"
)

scalacOptions ++= Seq(
	"-unchecked",
	 "-deprecation"
)

testOptions in Test ++= Seq(
	Tests.Argument(TestFrameworks.ScalaTest, "-oS")
)