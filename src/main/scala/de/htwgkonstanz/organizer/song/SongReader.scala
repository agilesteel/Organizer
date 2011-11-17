package de.htwgkonstanz.organizer.song

import org._
import jaudiotagger.audio._

import java._
import util.logging._
import util.logging.Level._

abstract class SongReader[ResultingTag] {
  Logger.getLogger("org.jaudiotagger").setLevel(OFF)

  def read(path: String): ResultingTag

  protected def readFile(filePath: String): AudioFile = {
    val file = new java.io.File(filePath)
    AudioFileIO.read(file)
  }
}