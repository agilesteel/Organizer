package de.htwgkonstanz.organizer.unit.delivery.tui

import de.htwgkonstanz._
import organizer.unit._
import organizer.song._
import organizer.delivery.tui._
import organizer.TestFiles._

class OrganizerControllerTests extends UnitTestConfiguration {
  class FakeModel extends Model {
    def parse(source: String, target: String): Seq[SongFile] = Seq[SongFile]()
    def preview(source: String, target: String) = Map[String, String]()
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
    controller.target should be(FileSystem.desktop + "/Organized Files")
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

  test("""Parse should yield Left("Directory not found")""") {
    val controller = new OrganizerController(new OrganizerModel)
    controller.parse match {
      case Left(error) => error should be("Directory not found")
    }
  }

  ignore("""Parse should yield Right()""") {
    val controller = new OrganizerController(new OrganizerModel).setSource(path)
    controller.parse match {
      case Right(songs) => {
        songs.toSet should be === (songFiles map { _.toString } toSet)
      }
    }
  }

  test("""Preview should yield Left("Directory not found")""") {
    val controller = new OrganizerController(new OrganizerModel)
    controller.preview match {
      case Left(error) => error should be("Directory not found")
    }
  }

  test("""Organize should yield Left("Directory not found")""") {
    val controller = new OrganizerController(new OrganizerModel)
    controller.organize match {
      case Left(error) => error should be("Directory not found")
    }
  }

}