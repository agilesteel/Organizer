package de.htwgkonstanz.organizer.delivery.tui

object TUI extends App {
  val model = new OrganizerModel
  val controller = new OrganizerController(model)
  val view = new OrganizerView(controller)
  view.display()
}