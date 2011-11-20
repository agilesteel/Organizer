package de.htwgkonstanz.organizer.unit.flac

import de.htwgkonstanz.organizer.io.FileSystem._
import de.htwgkonstanz.organizer._
import unit.UnitTestConfiguration
import song._
import flac._

class FlacParserTests extends UnitTestConfiguration {
  case class FakeReader(tag: SongTag) extends SongReader[SongTag] {
    def read(path: String): SongTag = tag
  }

  val file = File(List("location"), "name", "extension")

  val perfectSong = Song(
    Some("Title"),
    Some("Artist"),
    Some("Album"),
    Some("2011"),
    Some("0"))

  val perfectTag = SongTag("Title", "Artist", "Album", "2011", "0")

  test("Song should match the given tag in a perfect scenario") {
    val perfectReader = FakeReader(perfectTag)
    performTest(perfectReader, perfectSong)
  }

  test("Parser should fall back to None if info in id3v1 tag is missing") {
    val noTagReader = FakeReader(SongTag())
    performTest(noTagReader, Song())
  }

  private def performTest(reader: FakeReader, song: Song) {
    val parser = new FlacParser(reader)
    val actualSongFile = parser.parse(file)
    val expectedSongFile = SongFile(song, file)
    actualSongFile should be(expectedSongFile)
  }
}