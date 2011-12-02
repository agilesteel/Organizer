package de.htwgkonstanz.organizer.delivery.ui.textual

import de.htwgkonstanz.organizer.delivery.ui._

object TUI extends App {
  val model = new OrganizerModel
  val controller = new OrganizerController(model)
  val view = new OrganizerView(controller)
  view.display()
}