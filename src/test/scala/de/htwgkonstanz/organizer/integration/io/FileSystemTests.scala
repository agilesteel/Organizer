package de.htwgkonstanz.organizer.integration.io

import de.htwgkonstanz.organizer.integration._
import de.htwgkonstanz.organizer.io._
import de.htwgkonstanz.organizer._
import FileSystem._
import TestFiles._

class FileSystemTests extends TestConfiguration {
  feature("FileSystem should be aware of file existence") {
    scenario("exists is invoked on an existent file") {
      when("exists is invoked on " + thisFile)
      val actualValue = system.exists(thisFile)

      then("exists should return true")
      actualValue should equal(true)
    }

    scenario("exists is invoked on a non-existent file") {
      val nonExistentFile = File(List("location"), "name", "extension")
      when("exists is invoked on " + nonExistentFile)
      val actualValue = system.exists(nonExistentFile)

      then("exists should return false")
      actualValue should equal(false)
    }
  }

  feature("Copying files") {
    scenario("""copy is invoked on an existent file""") {
      given("a source file and a target name")
      val source = hellsBellsFile
      val target = File(List("target", "test"), "hells bells", Extensions.MP3)
      val File(targetLocation, targetName, _) = target

      try toCopy
      finally cleanUp

      def toCopy {
        when("copy is invoked on " + hellsBellsFile)
        system.copy(source, target)

        then(targetName + " should be copied to " + targetLocation)
        system.exists(targetLocation) should be(true)
        system.exists(target) should be(true)
      }

      def cleanUp {
        system.delete(target)
        system.delete(targetLocation)
      }
    }
  }

  private val system = FileSystem
  private def thisFile = {
    val locationPartsOfThisFile = "src" :: "test" :: "scala" :: this.getClass.getName.split("""\.""").dropRight(1).toList
    val nameOfThisFile = this.getClass.getSimpleName
    val extensionOfThisFile = "scala"
    File(locationPartsOfThisFile, nameOfThisFile, extensionOfThisFile)
  }
}