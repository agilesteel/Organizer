package de.htwgkonstanz.organizer.delivery.tui
import de.htwgkonstanz.organizer.song.SongFile

abstract class Controller {
  def status: Seq[String]
  
  def startOver: Controller
  def setSource(path: String): Controller
  def setTarget(path: String): Controller
  
  def parse: Either[String, Seq[String]]
  def preview: Either[String, Map[String, String]]
  def organize: Either[String, Unit]
  
  def areControllsEnabled: Boolean
  def isSourceSet: Boolean
}