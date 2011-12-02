package de.htwgkonstanz.organizer.delivery.ui.graphical

import de.htwgkonstanz.organizer.delivery.ui._

object GUI extends App {
  val model = new OrganizerModel
  val controller = new OrganizerController(model)
  val view = new OrganizerView(controller)
}