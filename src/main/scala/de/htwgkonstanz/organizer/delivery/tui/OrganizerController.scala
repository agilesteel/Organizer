package de.htwgkonstanz.organizer.delivery.tui

import de.htwgkonstanz.organizer.io.FileSystem._
import de.htwgkonstanz.organizer.song.SongFile

class OrganizerController private (val model: Model, val source: String, val target: String) extends Controller {
  def this(model: Model) = this(model, "", desktop + "/Organized Files")
  def areControllsEnabled = isSourceSet
  def isSourceSet = !source.isEmpty

  def status = List(
    "Source: " + source,
    "Target: " + target)

  def startOver: OrganizerController = new OrganizerController(model)
  def setSource(source: String): OrganizerController = new OrganizerController(model, source, target)
  def setTarget(target: String): OrganizerController = new OrganizerController(model, source, target)

  def parse: Either[String, Seq[String]] =
    try
      Right(model.parse(source, target) map { _.toString })
    catch {
      case e: Exception => Left(e.getMessage)
    }

  def preview: Either[String, Map[String, String]] =
    try
      Right(model.preview(source, target))
    catch {
      case e: Exception => Left(e.getMessage)
    }

  def organize: Either[String, Unit] =
    try
      Right(model.organize(source, target))
    catch {
      case e: Exception => Left(e.getMessage)
    }
}