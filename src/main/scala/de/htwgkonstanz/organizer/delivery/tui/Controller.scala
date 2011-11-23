package de.htwgkonstanz.organizer.delivery.tui

abstract class Controller {
  def menu: Seq[String]
  def status: Seq[String]
  def startOver: Controller
  def setSource(path: String): Controller
  def setTarget(path: String): Controller
  
  def preview: Seq[String]
  def parse: Seq[String]
  def organize(): Unit
  
  def areControllsEnabled: Boolean
  def isSourceSet: Boolean
}