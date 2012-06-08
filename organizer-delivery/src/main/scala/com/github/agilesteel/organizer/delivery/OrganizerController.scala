package com.github.agilesteel.organizer.delivery

import com.github.agilesteel.organizer.io.FileSystem._
import com.github.agilesteel.organizer.song.SongFile

class OrganizerController private (val model: Model, val source: String, val target: String) extends Controller {
  def this(model: Model) = this(model, "", desktop + "/Organized Files")
  def isSourceSet = !source.isEmpty

  def setSource(source: String): OrganizerController = new OrganizerController(model, source, target)
  def setTarget(target: String): OrganizerController = new OrganizerController(model, source, target)

  protected def tryParse = tryCallingErrorGeneratingRoutine {
    model.parse(source, target) map { _.toString } sortWith { _ < _}
  }

  protected def tryPreview = tryCallingErrorGeneratingRoutine {
    model.preview(source, target)
  }

  protected def tryOrganize = tryCallingErrorGeneratingRoutine {
    model.organize(source, target)
  }

  private def tryCallingErrorGeneratingRoutine[R](operation: => R) =
    try
      Right(operation)
    catch {
      case e: Exception => Left(e.getMessage)
    }
}