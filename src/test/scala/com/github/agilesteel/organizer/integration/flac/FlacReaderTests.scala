package com.github.agilesteel.organizer.integration.flac

import com.github.agilesteel.organizer._
import integration.IntegrationTestConfiguration
import flac._
import song._

class FlacReaderTests extends IntegrationTestConfiguration {
  feature("Flac Reader should read a flac file and produce a flac tag") {
    val path = """src/test/resources/dummy.flac"""
    scenario("read is invoked on " + path) {
      given("a flac reader")
      val reader = new FlacReader

      when("read is invoked on the reader")
      val actualTag = reader.read(path)

      then("tag should equal expected tags")
      val expectedTag = SongTag("Title", "Artist", "Album", "2011", "01")

      actualTag should equal(expectedTag)
    }
  }
}
