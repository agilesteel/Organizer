package de.htwgkonstanz.organizer.unit.delivery.tui

import de.htwgkonstanz.organizer.unit._
import de.htwgkonstanz.organizer.delivery.tui._

class OrganizerControllerTests extends UnitTestConfiguration {
  class FakeModel extends Model

  val model = new FakeModel
  val controller = new OrganizerController(model).setSource("source")

  test("This is what a default menu should look like") {
    controller.menu should be === List(
      "Here are your options:",
      "source | s",
      "target | t",
      "parse | pa",
      "preview | p",
      "organize | o",
      "start over | so",
      "quit | q")
  }

  test("isSourceSet should be set to false at the befinning") {
    val controller = new OrganizerController(model)
    controller.isSourceSet should not be (true)
  }

  test("All controlls should be enabled if source is set") {
    controller.areControllsEnabled should be(controller.isSourceSet)
  }

  test("SetSource should trigger isSourceSet") {
    controller.isSourceSet should be(true)
  }

  test("Parse, preview and organize should not be visible from the menu when all controlls aren't enabled") {
    val controller = new OrganizerController(model)
    controller.areControllsEnabled should not be (true)
    controller.menu should be === List(
      "Here are your options:",
      "source | s",
      "target | t",
      "start over | so",
      "quit | q")
  }

  test("Default source should be empty") {
    val controller = new OrganizerController(model)
    controller.source should be('empty)
  }

  test("Default target should be Desktop/Organized") {
    val controller = new OrganizerController(model)
    import de.htwgkonstanz.organizer.io.FileSystem._
    controller.target should be(desktop + "/Organizer")
  }

  test("SetTarget should be applied functionally") {
    val controller = new OrganizerController(model).setTarget("target")
    controller.target should be("target")
  }

  test("SetSource should be applied functionally") {
    val controller = new OrganizerController(model).setSource("source")
    controller.source should be("source")
  }

  test("Status should depend on source and target") {
    controller.status should be === List(
      "Source: " + controller.source,
      "Target: " + controller.target)
  }

  test("Refresh should yield an empty controller") {
    controller.startOver.status should be === (new OrganizerController(model).status)
  }

}