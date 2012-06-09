package com.github.agilesteel.organizer.delivery.graphical

import com.github.agilesteel.organizer.delivery._

object GUI extends App {
  val model = new OrganizerModel
  val controller = new OrganizerController(model)
  val view = new OrganizerView(controller)
}