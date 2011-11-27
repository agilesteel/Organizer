package de.htwgkonstanz.organizer.delivery.gui

import swing._
import de.htwgkonstanz.organizer.delivery.tui._

object GUI extends SimpleSwingApplication {
  val model = new OrganizerModel
  val controller = new OrganizerController(model)
  val view = new OrganizerView(controller)
  def top = view.display
}