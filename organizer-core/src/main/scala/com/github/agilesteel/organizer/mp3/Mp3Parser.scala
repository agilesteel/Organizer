package com.github.agilesteel.organizer.mp3

import com.github.agilesteel.organizer.song._

class Mp3Parser(val reader: SongReader[Mp3Tag]) extends SongParser[Mp3Tag] {
  def fallBack(tag: Mp3Tag)(property: SongTag => String): Option[String] =
    if (property(tag.id3v2).isEmpty)
      (if (property(tag.id3v1).isEmpty) None else Some(property(tag.id3v1)))
    else Some(property(tag.id3v2))
}