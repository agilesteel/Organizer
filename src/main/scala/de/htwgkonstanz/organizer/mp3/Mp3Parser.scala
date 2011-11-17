package de.htwgkonstanz.organizer.mp3

import de.htwgkonstanz.organizer.song._

class Mp3Parser(val reader: SongReader[Mp3Tag]) extends SongParser[Mp3Tag] {
  def fallBack(tag: Mp3Tag)(property: SongTag => String): Option[String] =
    if (property(tag.id3v2).isEmpty)
      (if (property(tag.id3v1).isEmpty) None else Some(property(tag.id3v1)))
    else Some(property(tag.id3v2))
}