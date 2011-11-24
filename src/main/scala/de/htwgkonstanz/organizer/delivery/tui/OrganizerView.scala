package de.htwgkonstanz.organizer.delivery.tui

import Console._
import OrganizerView._

object OrganizerView {
  implicit val defaultColor = RESET
}

class OrganizerView(var controller: Controller) {
  def display(): Unit = {
    presentGreeting()
    presentControlls()
  }

  private def presentGreeting() {
    present("Welcome to song organizer!")(YELLOW)
    present("Hold on a sec, I am initializing the neccessary components...")(YELLOW)
  }

  private def present(x: Any = "")(implicit color: String) {
    println(color + x + RESET + "\n")
  }

  private def presentControlls() {
    while (true) {
      presentStatus()
      presentMenu()
      controller = waitForInput()
    }
  }

  private def presentStatus() {
    presentList(controller.status)(GREEN)
  }

  private def presentMenu() {
    presentList(menu)
  }

  private def menu = {
    val beginning = List(
      "Here are your options:",
      "s  | source\t\tChange the source directory",
      "t  | target\t\tChange the target directory")

    val controlls = List(
      "pa | parse\t\tParse the source directory",
      "p  | preview\t\tPreview the target directory",
      "o  | organize\t\tCopy files from the source directory into the target directory")

    val ending = List(
      "so | start over\t\tClear source and target directories",
      "q  | quit\t\tClose the program",
      "e  | exit\t\tClose the program")

    if (controller.areControllsEnabled)
      beginning ::: controlls ::: ending
    else
      beginning ::: ending
  }

  private def presentList(list: scala.collection.GenTraversable[_])(implicit color: String) {
    present(list mkString "\n")(color)
  }

  private def waitForInput() = readLine match {
    case "source" | "s" => {
      present("In which directory are your unorganized songs?")
      controller.setSource(readLine)
    }
    case "target" | "t" => {
      present("Where should I organize your songs to?")
      controller.setTarget(readLine)
    }
    case "parse" | "pa" => {
      if (controller.areControllsEnabled) {
        present("Look what I've found")
        controller.parse.fold(presentError, presentList)
      } else
        presentWrongInputError()
      controller
    }
    case "preview" | "p" =>
      if (controller.areControllsEnabled) {
        present("This is how your new directory structure will look like:")
        controller.preview.fold(presentError, presentList)
      } else
        presentWrongInputError()
      controller
    case "organize" | "o" => {
      if (controller.areControllsEnabled) {
        present("This can take a while. You might wanna grab a snack...")
        controller.organize.fold(presentError, Unit => ())
      } else
        presentWrongInputError()
      controller
    }
    case "start over" | "so" => controller.startOver
    case "quit" | "q" | "exit" | "e" => {
      System.exit(0)
      controller
    }
    case unknownCommand => {
      presentWrongInputError()
      controller
    }
  }

  private def presentWrongInputError() {
    presentError("I beg your pardon?")
  }

  private def presentError(error: String) {
    present(error)(RED)
  }
}

