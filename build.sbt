name := "Organizer"

version := "1.1"

resolvers ++= Seq(
	"jaudiotagger repository" at "http://download.java.net/maven/2/org/jaudiotagger",
	"Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)

libraryDependencies ++= Seq(
	"org.jaudiotagger" % "jaudiotagger" % "latest.release",
	"org.scalaz" %% "scalaz-core" % "latest.release"
)

scalacOptions ++= Seq(
	"-unchecked",
	 "-deprecation"
)