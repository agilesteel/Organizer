package com.github.agilesteel.organizer.delivery

import com.github.agilesteel.organizer._
import song.SongFile

class OrganizerControllerTests extends UnitTestConfiguration {
  class FakeModel extends Model {
    def parse(source: String, target: String): Seq[SongFile] = Seq[SongFile]()
    def preview(source: String, target: String) = Map[String, String]()
    def organize(source: String, target: String): Unit = {}
  }

  val model = new FakeModel
  val controller: Controller = new OrganizerController(model)
  val controllerWithSource = controller.setSource("source")
  val controllerWithoutSourceButWithRealModel = new OrganizerController(new OrganizerModel).setSource("non-existent source")

  test("Controller should have source and target") {
    controller should have(
      'source(""),
      'target(com.github.agilesteel.organizer.io.FileSystem.desktop + "/Organized Files"),
      'isSourceSet(false),
      'areControllsEnabled(controller.isSourceSet),
      'areControllsDisabled(!controller.areControllsEnabled))
  }

  test("All controlls should be enabled if source is set") {
    controllerWithSource.areControllsEnabled should be(controllerWithSource.isSourceSet)
  }

  test("SetSource should trigger isSourceSet") {
    controllerWithSource.isSourceSet should be(true)
  }

  test("SetTarget should be applied functionally") {
    val controllerWithTarget = controller.setTarget("target")
    controllerWithTarget.target should be("target")
  }

  test("SetSource should be applied functionally") {
    controllerWithSource.source should be("source")
  }

  test("""Parse should yield Left("Directory not found")""") {
    controllerWithoutSourceButWithRealModel.parse match {
      case Left(error) => error should be("Directory not found")
    }
  }

  test("""Parse should yield Right()""") {
    import TestFiles._
    val controller = new OrganizerController(new OrganizerModel).setSource(path)
    controller.parse match {
      case Right(songs) => {
        songs.toSet should be === (songFiles map { _.toString } toSet)
      }
    }
  }

  test("""Preview should yield Left("Directory not found")""") {
    controllerWithoutSourceButWithRealModel.preview match {
      case Left(error) => error should be("Directory not found")
    }
  }

  test("""Organize should yield Left("Directory not found")""") {
    controllerWithoutSourceButWithRealModel.organize match {
      case Left(error) => error should be("Directory not found")
    }
  }

  testLeftIfControllsAreDisabled("Parse") {
    controller.parse
  }

  private def testLeftIfControllsAreDisabled(operationName: String)(eitherOperation: => Either[String, _]) {
    test(operationName + """ should yield Left("Please tell me where your songs are")""") {
      eitherOperation match {
        case Left(error) => error should be("Please tell me where your songs are")
      }
    }
  }

  testLeftIfControllsAreDisabled("Preview") {
    controller.preview
  }

  testLeftIfControllsAreDisabled("Organize") {
    controller.organize
  }

}