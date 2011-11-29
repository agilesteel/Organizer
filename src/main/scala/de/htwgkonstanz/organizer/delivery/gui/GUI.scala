package de.htwgkonstanz.organizer.delivery.gui

import de.htwgkonstanz.organizer.delivery.tui._

object GUI extends App {
  val model = new OrganizerModel
  val controller = new OrganizerController(model)
  val view = new OrganizerView(controller)
}