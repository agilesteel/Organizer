package de.htwgkonstanz.organizer.delivery.ui

import de.htwgkonstanz.organizer.song._

abstract class Model {
  def parse(source: String, target: String): Seq[SongFile]
  def preview(source: String, target: String): Map[String, String]
  def organize(source: String, target: String): Unit
}