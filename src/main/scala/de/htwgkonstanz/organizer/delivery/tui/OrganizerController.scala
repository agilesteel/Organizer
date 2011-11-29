package de.htwgkonstanz.organizer.delivery.tui

import de.htwgkonstanz.organizer.io.FileSystem._
import de.htwgkonstanz.organizer.song.SongFile

class OrganizerController private (val model: Model, val source: String, val target: String) extends Controller {
  def this(model: Model) = this(model, "", desktop + "/Organized Files")
  def isSourceSet = !source.isEmpty

  def status = List(
    "Source: " + source,
    "Target: " + target)

  def setSource(source: String): OrganizerController = new OrganizerController(model, source, target)
  def setTarget(target: String): OrganizerController = new OrganizerController(model, source, target)

  protected def tryParse = tryCallingErrorGeneratingRoutine {
    model.parse(source, target) map { _.toString }
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