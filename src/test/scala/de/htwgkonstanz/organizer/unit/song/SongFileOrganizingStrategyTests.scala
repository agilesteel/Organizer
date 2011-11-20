package de.htwgkonstanz.organizer.unit.song

import de.htwgkonstanz.organizer.song._
import de.htwgkonstanz.organizer._
import unit.UnitTestConfiguration
import io._
import FileSystem._

class SongFileOrganizingStrategyTests extends UnitTestConfiguration {
  test("Target directory should be set") {
    strategy.targetDirectory should be(targetDirectory)
  }

  test("Perfect mp3 strategy") {
    testPerfectMp3Strategy(perfectSong())
  }

  private def testPerfectMp3Strategy(perfectSong: Song) {
    val actualMp3FileInfo = strategy.apply(create(perfectSong))

    val expectedName = "Artist - Title"
    val assignedLocation = "A" + separator + "Artist" + separator + "(2011) - Album"
    val expectedMp3FileInfo = create(List(targetDirectory, assignedLocation), expectedName)
    actualMp3FileInfo should be(expectedMp3FileInfo)
  }

  test("Unsorted directory should be a child of targetDirectory") {
    strategy.unassignedDirectoryParts should be(List(targetDirectory, "Unassigned"))
  }

  test("If artist is less than one charakter long, the song info can't be assigned to a file info") {
    val song = perfectSong(artist = Some(""))
    ensureInvalidSongInfoResultsCopyingIntoTheUnassignedDirectory(create(song))
  }

  private def ensureInvalidSongInfoResultsCopyingIntoTheUnassignedDirectory(songFile: SongFile) {
    val actualMp3FileInfo = strategy.apply(songFile)
    val expectedName = songFile.song.title.get
    val expectedLocation = strategy.unassignedDirectoryParts
    val expectedMp3FileInfo = create(expectedLocation, expectedName)
    actualMp3FileInfo should be(expectedMp3FileInfo)
  }

  test("If year is not assigned, the song info can't be assigned to a file info") {
    val song = perfectSong(year = None)
    ensureInvalidSongInfoResultsCopyingIntoTheUnassignedDirectory(create(song))
  }

  test("If album is not assigned, the song info can't be assigned to a file info") {
    val song = perfectSong(album = None)
    ensureInvalidSongInfoResultsCopyingIntoTheUnassignedDirectory(create(song))
  }

  test("If title is not assigned, the song info can't be assigned to a file info") {
    val song = perfectSong(title = None)
    val actualMp3FileInfo = strategy.apply(create(song))

    val File(_, expectedName, _) = file
    val expectedLocation = strategy.unassignedDirectoryParts
    val expectedMp3FileInfo = create(expectedLocation, expectedName)
    actualMp3FileInfo should be(expectedMp3FileInfo)
  }

  test("Invalid characters should be removed when they appear in the beginning of a property") {
    val songWithInvalidCharacters = perfectSong(
      title = Some(invalidCharacters + "Title"),
      album = Some(invalidCharacters + "Album"),
      year = Some(invalidCharacters + "2011"))

    testPerfectMp3Strategy(songWithInvalidCharacters)
  }

  test("Invalid characters should be removed when they appear in the ending of a property") {
    val songWithInvalidCharacters = perfectSong(
      title = Some("Title" + invalidCharacters),
      artist = Some("Artist" + invalidCharacters),
      album = Some("Album" + invalidCharacters),
      year = Some("2011" + invalidCharacters))

    testPerfectMp3Strategy(songWithInvalidCharacters)
  }

  test("Invalid characters should be replaced with a space when they appear in the middle of a property") {
    val songWithInvalidCharacters = perfectSong(
      title = Some("Tit" + invalidCharacters + "le"),
      album = Some("A" + invalidCharacters + "lbum"),
      year = Some("201" + invalidCharacters + "1"))

    val actualMp3FileInfo = strategy.apply(create(songWithInvalidCharacters))

    val expectedName = "Artist - Tit le"
    val assignedLocation = "A" + separator + "Artist" + separator + "(201 1) - A lbum"
    val expectedMp3FileInfo = create(List(targetDirectory, assignedLocation), expectedName)
    actualMp3FileInfo should be(expectedMp3FileInfo)
  }

  test("If artist starts with an invalid character, the song info can't be assigned to a file info") {
    val song = perfectSong(artist = Some(invalidCharacters.head.toString))
    ensureInvalidSongInfoResultsCopyingIntoTheUnassignedDirectory(create(song))
  }

  test("If artist is invalid, make sure that invalid characters of the title are handled properly") {
    val song = perfectSong(title = Some(invalidCharacters + "Title"), artist = None)

    val actualMp3FileInfo = strategy.apply(create(song))

    val expectedName = "Title"
    val expectedLocation = strategy.unassignedDirectoryParts
    val expectedMp3FileInfo = create(expectedLocation, expectedName)
    actualMp3FileInfo should be(expectedMp3FileInfo)
  }

  private val targetDirectory = "Target Directory"
  private val strategy = new SongFileOrganizingStrategy(targetDirectory)

  private def perfectSong(
    title: Option[String] = Some("Title"),
    artist: Option[String] = Some("Artist"),
    album: Option[String] = Some("Album"),
    year: Option[String] = Some("2011"),
    trackNumber: Option[String] = Some("0")) = Song(title, artist, album, year, trackNumber)

  private val file = create(List("location"), "name")
  private def create(locationParts: List[String], name: String) = File(locationParts, name, "mp3")
  private def create(song: Song): SongFile = SongFile(song, file)
}