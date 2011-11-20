package de.htwgkonstanz.organizer.unit.mp3

import de.htwgkonstanz.organizer._
import unit.UnitTestConfiguration
import io._
import song._
import mp3._
import FileSystem._

class Mp3ParserTests extends UnitTestConfiguration {
  test("Song should match id3v2 tag in a perfect scenario") {
    val perfectReader = FakeReader(Mp3Tag(perfectTag, perfectTag))
    performTest(perfectReader, perfectSong)
  }

  test("Parser should fall back to id3v1 tag if info in id3v2 tag is missing") {
    val noId3v2Reader = FakeReader(Mp3Tag(perfectTag, SongTag()))
    performTest(noId3v2Reader, perfectSong)
  }

  test("Parser should fall back to None if info in id3v1 tag is missing") {
    val noId3v1Reader = FakeReader(Mp3Tag(SongTag(), SongTag()))
    performTest(noId3v1Reader, Song())
  }

  private final case class FakeReader(tag: Mp3Tag) extends SongReader[Mp3Tag] {
    def read(path: String): Mp3Tag = tag
  }

  private val file = File(List("location"), "name", "extension")

  private val perfectSong = Song(
    Some("Title"),
    Some("Artist"),
    Some("Album"),
    Some("2011"),
    Some("0"))

  val perfectTag = SongTag("Title", "Artist", "Album", "2011", "0")
  
  private def performTest(reader: FakeReader, song: Song) {
    val parser = new Mp3Parser(reader)
    val actualSongFile = parser.parse(file)
    val expectedSongFile = SongFile(song, file)
    actualSongFile should be(expectedSongFile)
  }
}