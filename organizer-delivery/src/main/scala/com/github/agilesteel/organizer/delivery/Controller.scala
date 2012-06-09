package com.github.agilesteel.organizer.delivery

import com.github.agilesteel.organizer.song.SongFile
import Controller._

object Controller {
  val controllsDisabledError = Left("Please tell me where your songs are")
}

abstract class Controller {
  val source: String
  val target: String

  def setSource(path: String): Controller
  def setTarget(path: String): Controller

  def areControllsDisabled: Boolean = !areControllsEnabled
  def areControllsEnabled: Boolean = isSourceSet
  def isSourceSet: Boolean

  def parse: Either[String, Seq[String]] = callEitherGeneratingRoutine(tryParse)
  def preview: Either[String, Map[String, String]] = callEitherGeneratingRoutine(tryPreview)
  def organize: Either[String, Unit] = callEitherGeneratingRoutine(tryOrganize)

  private def callEitherGeneratingRoutine[R](operation: => Either[String, R]) =
    if (areControllsEnabled)
      operation
    else controllsDisabledError

  protected def tryParse: Either[String, Seq[String]]
  protected def tryPreview: Either[String, Map[String, String]]
  protected def tryOrganize: Either[String, Unit]
}