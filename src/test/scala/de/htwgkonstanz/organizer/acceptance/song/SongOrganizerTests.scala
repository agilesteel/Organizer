package de.htwgkonstanz.organizer.acceptance.song

import de.htwgkonstanz.organizer.acceptance._
import de.htwgkonstanz.organizer.song._
import de.htwgkonstanz.organizer._
import io._
import FileSystem._
import TestFiles._

class SongOrganizerTests extends AcceptanceTestConfiguration {
  feature("User should be able to parse a list of flac/mp3 files " +
    "transforming each of them into a data structure " +
    "containing the path of a file with according song information") {
    scenario("parse is invoked on a list of flac/mp3 files") {
      val path = """src\test\resources"""
      when("parse is invoked on " + path)
      val actualSongFiles = organizer.parse(path)

      then("the result should be a list of song files containing" + prettyPrint(songFiles map { _.song }))
      val expectedSongFiles = songFiles
      actualSongFiles === expectedSongFiles
    }
  }

  feature("User should be able to preview his new directory structure") {
    scenario("preview is invoked on a list of song files using a simple organizing strategy") {
      when("preview is invoked on:" + prettyPrint(songFiles))
      val actualMap = organizer.preview(songFiles)

      then("the result should be a map from song files to paths containing" + prettyPrint(map.values))
      val expectedMap = map
      actualMap === expectedMap
    }
  }

  feature("User should be able to organize a list of song files " +
    "by copying the files according to the organizing strategy embedded in the organizer") {
    scenario("organize is invoked on a list of flac/mp3 files") {
      def file(name: String) = File(targetDirectoryParts, name, Extensions.MP3)
      val flac = File(targetDirectory, "Title", Extensions.FLAC)
      val tempSongFiles = List(flac, file("(1997) -----"), file("Hells Bells"), file("I Want It All"))

      try toCopy
      finally cleanUp

      def toCopy {
        when("organize is invoked on:" + prettyPrint(map.values))
        organizer.organize(songFiles)

        then(files + " should be copied into " + targetDirectory)
        tempSongFiles forall fileSystem.exists should be(true)
      }

      def cleanUp {
        tempSongFiles foreach fileSystem.delete
        fileSystem.delete(targetDirectory)
      }
    }
  }

  private val targetDirectoryParts = List("target", "test")
  private val targetDirectory = targetDirectoryParts mkString separator
  private val strategy = (songFile: SongFile) => {
    val File(_, _, extension) = songFile.filePath
    File(targetDirectoryParts, songFile.song.title.get, extension)
  }
  private val map = songFiles map { songFile => (songFile, strategy(songFile)) } toMap
  private val fileSystem = FileSystem
  private val organizer = new SongOrganizer(strategy)
}