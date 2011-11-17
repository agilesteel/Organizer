package de.htwgkonstanz.organizer.unit.song

import de.htwgkonstanz.organizer._
import unit.TestConfiguration
import de.htwgkonstanz.organizer.song._

class SongTests extends TestConfiguration {
  test("All song members should be initially set to None") {
    val song = Song()
    import song._
    val members = List(title, artist, album, year, track)
    members forall { None == } should be(true)
  }
  
  test("toString should unpack options replacing None with an empty string") {
    val song = Song(Some("Title"), Some("Artist"), None, Some("2011"), Some("5"))
    val expectedString = "Song(Title,Artist,,2011,5)"
      
    song.toString should be(expectedString)
  }
}