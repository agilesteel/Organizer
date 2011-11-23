package de.htwgkonstanz.organizer.delivery.tui

import de.htwgkonstanz.organizer.io.FileSystem._

class OrganizerController private (val model: Model, val source: String, val target: String) extends Controller {
  def this(model: Model) = this(model, "", desktop + "/Organizer")
  def areControllsEnabled = isSourceSet
  def isSourceSet = !source.isEmpty

  def menu = {
    val beginning = List(
      "Here are your options:",
      "source | s",
      "target | t")

    val controlls = List(
      "parse | pa",
      "preview | p",
      "organize | o")

    val ending = List(
      "start over | so",
      "quit | q")

    if (areControllsEnabled) beginning ::: controlls ::: ending
    else beginning ::: ending
  }

  def status = List(
    "Source: " + source,
    "Target: " + target)

  def startOver: OrganizerController = new OrganizerController(model)
  def setSource(source: String): OrganizerController = new OrganizerController(model, source, target)
  def setTarget(target: String): OrganizerController = new OrganizerController(model, source, target)
  
  def parse = Nil
  def preview = Nil
  def organize() {}
}