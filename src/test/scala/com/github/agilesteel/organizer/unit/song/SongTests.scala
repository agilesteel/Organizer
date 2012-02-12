package com.github.agilesteel.organizer.unit.song

import com.github.agilesteel.organizer._
import unit.UnitTestConfiguration
import com.github.agilesteel.organizer.song._

class SongTests extends UnitTestConfiguration {
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