package com.github.agilesteel.organizer

import song._
import io._
import FileSystem._

object TestFiles {
  lazy val path = """organizer-core/src/test/resources"""

  lazy val locationParts = path.split("/").toList
  lazy val childParts = locationParts :+ "child"

  lazy val dummyName = "dummy"

  def createFlacFile(locParts: List[String] = locationParts, name: String = dummyName) = File(locParts, name, KnownFileTypes.FLAC)
  def createMp3File(locParts: List[String] = locationParts, name: String = dummyName) = File(locParts, name, KnownFileTypes.MP3)

  lazy val dummyFlacFile = createFlacFile()
  lazy val dummyMp3File = createMp3File()
  lazy val hellsBellsFile = createMp3File(name = "01 - AC-DC-Hells Bells")
  lazy val iWantItAllFile = createMp3File(childParts, "Queen - I Want It All")
  lazy val noTagsFile = createMp3File(childParts, "No Tags")

  lazy val files = List(dummyFlacFile, dummyMp3File, hellsBellsFile, iWantItAllFile, noTagsFile)

  lazy val dummyFlacSong = Song(Some("Title"), Some("Artist"), Some("Album"), Some("2011"), Some("01"))
  lazy val dummyMp3Song = Song(Some("(1997) -----"), Some("----- Tree Dollar Bill, Yalls"))
  lazy val hellsBellsSong = Song(Some("Hells Bells"), Some("AC/DC"), Some("Back In Black"), Some("1980"), Some("1"))
  lazy val iWantItAllSong = Song(Some("I Want It All"), Some("Queen"), Some("Collection 2000"), Some("2000"), Some("4"))
  lazy val noTagsSong = Song()

  lazy val dummyFlacSongFile = SongFile(dummyFlacSong, dummyFlacFile)
  lazy val dummyMp3SongFile = SongFile(dummyMp3Song, dummyMp3File)
  lazy val hellsBellsSongFile = SongFile(hellsBellsSong, hellsBellsFile)
  lazy val iWantItAllSongFile = SongFile(iWantItAllSong, iWantItAllFile)
  lazy val noTagsSongFile = SongFile(noTagsSong, noTagsFile)

  lazy val songFiles = List(dummyFlacSongFile, dummyMp3SongFile, hellsBellsSongFile, iWantItAllSongFile, noTagsSongFile)
}