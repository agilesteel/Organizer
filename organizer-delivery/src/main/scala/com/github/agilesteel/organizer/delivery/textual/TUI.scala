package com.github.agilesteel.organizer.delivery.textual

import com.github.agilesteel.organizer.delivery._

object TUI extends App {
  val model = new OrganizerModel
  val controller = new OrganizerController(model)
  val view = new OrganizerView(controller)
  view.display()
}