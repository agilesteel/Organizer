package com.github.agilesteel.organizer.flac

import com.github.agilesteel.organizer.song._

class FlacParser(override val reader: SongReader[SongTag]) extends SongParser[SongTag] {
  def fallBack(tag: SongTag)(property: SongTag => String): Option[String] =
    if (property(tag).isEmpty) None else Some(property(tag))
}