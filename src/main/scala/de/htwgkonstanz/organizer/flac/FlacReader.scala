package de.htwgkonstanz.organizer.flac

import de.htwgkonstanz.organizer.song._

import org._
import jaudiotagger.tag.FieldKey._
import jaudiotagger.tag.{ Tag => TaggerTag }

class FlacReader extends SongReader[SongTag]{
  def read(filePath: String): SongTag = {
    val flac = readFile(filePath)
    
    val tag = flac.getTag

    SongTag(
      tag.getFirst(TITLE),
      tag.getFirst(ARTIST),
      tag.getFirst(ALBUM),
      tag.getFirst(YEAR),
      tag.getFirst(TRACK))
  }
}