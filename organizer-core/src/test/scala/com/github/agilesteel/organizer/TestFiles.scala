package com.github.agilesteel.organizer

import song._
import io._
import FileSystem._

object TestFiles {
  val path = """src/test/resources"""

  val locationParts = path.split("/").toList
  def childParts(locParts: List[String]) = locParts :+ "child"

  val dummyName = "dummy"

  def createFlacFile(locParts: List[String] = locationParts, name: String = dummyName) = File(locParts, name, KnownFileTypes.FLAC)
  def createMp3File(locParts: List[String] = locationParts, name: String = dummyName) = File(locParts, name, KnownFileTypes.MP3)

  def dummyFlacFile(locParts: List[String] = locationParts) = createFlacFile(locParts)
  def dummyMp3File(locParts: List[String] = locationParts) = createMp3File(locParts)
  def hellsBellsFile(locParts: List[String] = locationParts) = createMp3File(locParts = locParts, name = "01 - AC-DC-Hells Bells")
  def iWantItAllFile(locParts: List[String] = locationParts) = createMp3File(childParts(locParts), "Queen - I Want It All")
  def noTagsFile(locParts: List[String] = locationParts) = createMp3File(childParts(locParts), "No Tags")

  def files(implicit locParts: List[String] = locationParts) = List(dummyFlacFile(locParts), dummyMp3File(locParts), hellsBellsFile(locParts), iWantItAllFile(locParts), noTagsFile(locParts))

  val dummyFlacSong = Song(Some("Title"), Some("Artist"), Some("Album"), Some("2011"), Some("01"))
  val dummyMp3Song = Song(Some("(1997) -----"), Some("----- Tree Dollar Bill, Yalls"))
  val hellsBellsSong = Song(Some("Hells Bells"), Some("AC/DC"), Some("Back In Black"), Some("1980"), Some("1"))
  val iWantItAllSong = Song(Some("I Want It All"), Some("Queen"), Some("Collection 2000"), Some("2000"), Some("4"))
  val noTagsSong = Song()

  def dummyFlacSongFile(implicit locParts: List[String] = locationParts) = SongFile(dummyFlacSong, dummyFlacFile(locParts))
  def dummyMp3SongFile(implicit locParts: List[String] = locationParts) = SongFile(dummyMp3Song, dummyMp3File(locParts))
  def hellsBellsSongFile(implicit locParts: List[String] = locationParts) = SongFile(hellsBellsSong, hellsBellsFile(locParts))
  def iWantItAllSongFile(implicit locParts: List[String] = locationParts) = SongFile(iWantItAllSong, iWantItAllFile(locParts))
  def noTagsSongFile(implicit locParts: List[String] = locationParts) = SongFile(noTagsSong, noTagsFile(locParts))

  def songFiles(implicit locParts: List[String] = locationParts) = List(dummyFlacSongFile, dummyMp3SongFile, hellsBellsSongFile, iWantItAllSongFile, noTagsSongFile)
}