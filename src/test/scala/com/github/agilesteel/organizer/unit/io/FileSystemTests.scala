package com.github.agilesteel.organizer.unit.io

import com.github.agilesteel.organizer.io._
import com.github.agilesteel.organizer.io.FileSystem._
import com.github.agilesteel.organizer._
import unit.UnitTestConfiguration

class FileSystemTests extends UnitTestConfiguration {
  test("Perfect file path should be recognized") {
    val filePath = "location" + separator + "name.extension"
    assertDefaults(filePath)
  }

  private def assertDefaults(filePath: String, location: String = "location") {
    val File(location, name, extension) = filePath
    location should be(location)
    name should be("name")
    extension should be("extension")
  }

  test("Nested file path should be recognized") {
    val location = "folder1" + separator + "folder2"
    val filePath = location + separator + "name.extension"
    assertDefaults(filePath, location)
  }

  test("Null as path should produce an exception") {
    evaluating { val File(location, name, extension) = null } should produce[MatchError]
  }

  test("Directory path should produce an exception") {
    val dirPath = "parent" + separator + "child"
    evaluating { val File(location, name, extension) = dirPath } should produce[MatchError]
  }

  test("""Injection method for the location: String, name: String, extension: String pattern should be available""") {
    val filePath = File("location", "name", "extension")
    assertDefaults(filePath)
  }

  test("""Injection method for the locationParts: Seq[String], name: String, extension: String pattern should be available""") {
    val filePath = File(List("parent", "child"), "name", "extension")
    assertDefaults(filePath, "parent" + separator + "child")
  }
}