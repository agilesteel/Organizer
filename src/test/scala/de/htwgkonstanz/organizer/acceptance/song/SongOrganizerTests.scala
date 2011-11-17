package de.htwgkonstanz.organizer.acceptance.song

import de.htwgkonstanz.organizer.acceptance._
import de.htwgkonstanz.organizer.song._
import de.htwgkonstanz.organizer._
import io._
import FileSystem._
import TestFiles._


class SongOrganizerTests extends TestConfiguration {
  feature("Song organizer should recursively traverse a source directory gathering flac/mp3 files") {
    scenario("traverse is invoked on an existent source directory") {
      when("""traverse is invoked on src\test\resources""")
      val path = """src\test\resources"""
      val actualFiles = organizer.traverse(path)

      then("the result should be a list of files containing" + prettyPrint(files))
      val expectedFiles = files
      actualFiles === expectedFiles
    }
  }

  feature("Song organizer should parse a list of flac/mp3 files " +
    "transforming each of them into a data structure " +
    "containing the path of a file with according song information") {
    scenario("parse is invoked on a list of flac/mp3 files") {
      when("parse is invoked on " + files)
      val actualSongFiles = organizer.parse(files)

      then("the result should be a list of song files containing" + prettyPrint(songFiles map { _.song }))
      val expectedSongFiles = songFiles
      actualSongFiles === expectedSongFiles
    }
  }

  feature("User should be able to preview his new directory structure") {
    scenario("preview is invoked on a list of song files using a simple organizing strategy") {
      when("preview is invoked on:" + prettyPrint(songFiles))
      val actualMap = organizer.preview(songFiles, strategy)

      then("the result should be a map from song files to paths containing" + prettyPrint(map.values))
      val expectedMap = map
      actualMap === expectedMap
    }
  }

  feature("Song organizer should organize a map from old song files to new files " +
    "by copying the files according to the simple strategy embedded in the map") {
    scenario("organize is invoked on a map from flac/mp3 files to song informations") {
      def file(name: String) = File(targetDirectoryParts, name, Extensions.MP3)
      val flac = File(targetDirectory, "Title", Extensions.FLAC)
      val tempSongFiles = List(flac, file("(1997) -----"), file("Hells Bells"), file("I Want It All"))

      try toCopy
      finally cleanUp

      def toCopy {
        when("organize is invoked on:" + prettyPrint(map.values))
        organizer.organize(map)
     
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
    val File(_,_, extension) = songFile.filePath
    File(targetDirectoryParts, songFile.song.title.get, extension)
  }
  private val map = songFiles map { songFile => (songFile, strategy(songFile)) } toMap
  private val fileSystem = FileSystem
  private val organizer = new SongOrganizer
  private def prettyPrint(collection: scala.collection.GenTraversableOnce[_]) = collection mkString ("\n\t\t", "\n\t\t", "")
}