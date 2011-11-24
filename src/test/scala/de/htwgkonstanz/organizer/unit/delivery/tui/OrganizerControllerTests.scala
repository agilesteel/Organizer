package de.htwgkonstanz.organizer.unit.delivery.tui

import de.htwgkonstanz.organizer.unit._
import de.htwgkonstanz.organizer.delivery.tui._
import de.htwgkonstanz.organizer.song.SongFile

class OrganizerControllerTests extends UnitTestConfiguration {
  class FakeModel extends Model {
    def parse(source: String, target: String): Seq[SongFile] = Seq[SongFile]()
    def preview(source: String, target: String) = Map[SongFile, String]()
    def organize(source: String, target: String): Unit = {}
  }

  val model = new FakeModel
  val controller = new OrganizerController(model)
  val controllerWithSource = controller.setSource("source")

  test("isSourceSet should be set to false at the befinning") {
    controller.isSourceSet should not be (true)
  }

  test("All controlls should be enabled if source is set") {
    controllerWithSource.areControllsEnabled should be(controllerWithSource.isSourceSet)
  }

  test("SetSource should trigger isSourceSet") {
    controllerWithSource.isSourceSet should be(true)
  }

  test("Default source should be empty") {
    controller.source should be('empty)
  }

  test("Default target should be Desktop/Organized") {
    import de.htwgkonstanz.organizer.io._
    controller.target should be(FileSystem.desktop + "/Organizer")
  }

  test("SetTarget should be applied functionally") {
    val controllerWithTarget = controller.setTarget("target")
    controllerWithTarget.target should be("target")
  }

  test("SetSource should be applied functionally") {
    controllerWithSource.source should be("source")
  }

  test("Status should depend on source and target") {
    controllerWithSource.status should be === List(
      "Source: " + controllerWithSource.source,
      "Target: " + controllerWithSource.target)
  }

  test("Refresh should yield an empty controller") {
    controllerWithSource.startOver.status should be === (new OrganizerController(model).status)
  }
}