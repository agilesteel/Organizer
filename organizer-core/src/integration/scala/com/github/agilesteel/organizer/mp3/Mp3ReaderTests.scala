package com.github.agilesteel.organizer.mp3

import com.github.agilesteel.organizer._
import mp3._
import song._

class Mp3ReaderTests extends IntegrationTestConfiguration {
  feature("Mp3 Reader should read an mp3 file and produce mp3 tags") {
    scenario("read is invoked on Hells Bells from AC/DC") {
      given("an mp3 reader")
      val reader = new Mp3Reader

      when("read is invoked on the reader")
      val path = TestFiles.path + "/01 - AC-DC-Hells Bells.mp3"
      val actualTag = reader.read(path)

      then("tags should equal expected tags")
      val v1 = SongTag("", "AC/DC", "Back In Black", "1980")
      val v2 = SongTag("Hells Bells", "", "Back In Black", "1980", "1")
      val expectedTag = Mp3Tag(v1, v2)

      actualTag should equal(expectedTag)
    }
  }
}
