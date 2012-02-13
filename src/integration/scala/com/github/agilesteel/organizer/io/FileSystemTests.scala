package com.github.agilesteel.organizer.io

import com.github.agilesteel.organizer._
import FileSystem._
import KnownFileTypes._
import TestFiles._

class FileSystemTests extends IntegrationTestConfiguration {
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
      val target = File(List("target", "test"), "hells bells", MP3)
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

  feature("Traversing a directory") {
    scenario("traverse is invoked on an existent source directory") {
      when("""traverse is invoked on src\test\resources""")
      val path = """src/test/resources"""
      val actualFiles = system.traverse(path)(MP3, FLAC)

      then("the result should be a list of files containing" + prettyPrint(files))
      val expectedFiles = files
      actualFiles === expectedFiles
    }
  }

  private val system = FileSystem
  private def thisFile = {
    val locationPartsOfThisFile = "src" :: "integration" :: "scala" :: this.getClass.getName.split("""\.""").dropRight(1).toList
    val nameOfThisFile = this.getClass.getSimpleName
    val extensionOfThisFile = "scala"
    File(locationPartsOfThisFile, nameOfThisFile, extensionOfThisFile)
  }
}
