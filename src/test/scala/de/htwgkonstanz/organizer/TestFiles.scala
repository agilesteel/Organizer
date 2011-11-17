package de.htwgkonstanz.organizer

import de.htwgkonstanz.organizer._
import song._
import io._
import FileSystem._

object TestFiles {
  val childParts = List("src", "test", "resources", "child")
  val locationParts = childParts.dropRight(1)
  val dummyName = "dummy"
  
  def createFlacFile(locParts: List[String] = locationParts, name: String = dummyName) = File(locParts, name, Extensions.FLAC)
  def createMp3File(locParts: List[String] = locationParts, name: String = dummyName) = File(locParts, name, Extensions.MP3)
  
  val dummyFlacFile = createFlacFile()
  val dummyMp3File = createMp3File()
  val hellsBellsFile = createMp3File(name = "01 - AC-DC-Hells Bells")
  val iWantItAllFile = createMp3File(childParts, "Queen - I Want It All")
    
  val files = List(dummyFlacFile, dummyMp3File, hellsBellsFile, iWantItAllFile)
  
  val dummyFlacSong = Song(Some("Title"), Some("Artist"), Some("Album"), Some("2011"), Some("01"))
  val dummyMp3Song = Song(Some("(1997) -----"), Some("----- Tree Dollar Bill, Yalls"))
  val hellsBellsSong = Song(Some("Hells Bells"), Some("AC/DC"), Some("Back In Black"), Some("1980"), Some("1"))
  val iWantItAllSong = Song(Some("I Want It All"), Some("Queen"), Some("Collection 2000"), Some("2000"), Some("4"))
  
  val dummyFlacSongFile = SongFile(dummyFlacSong, dummyFlacFile)
  val dummyMp3SongFile = SongFile(dummyMp3Song, dummyMp3File)
  val hellsBellsSongFile = SongFile(hellsBellsSong, hellsBellsFile)
  val iWantItAllSongFile = SongFile(iWantItAllSong, iWantItAllFile)
  
  val songFiles = List(dummyFlacSongFile, dummyMp3SongFile, hellsBellsSongFile, iWantItAllSongFile)
}