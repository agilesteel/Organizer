package com.github.agilesteel.organizer.mp3

import com.github.agilesteel.organizer.song._

import org._
import jaudiotagger.audio.mp3.MP3File
import jaudiotagger.tag.FieldKey._
import jaudiotagger.tag.{ Tag => TaggerTag }

class Mp3Reader extends SongReader[Mp3Tag] {
  def read(filePath: String): Mp3Tag = {
    val mp3 = readFile(filePath).asInstanceOf[MP3File]

    val id3v1 = mp3.getID3v1Tag
    val id3v2 = mp3.getTag

    Mp3Tag(tag(id3v1), tag(id3v2))
  }

  private def tag(tag: TaggerTag) =
    if (tag == null)
      SongTag()
    else
      SongTag(
        tag.getFirst(TITLE),
        tag.getFirst(ARTIST),
        tag.getFirst(ALBUM),
        tag.getFirst(YEAR),
        retrieveTrackFrom(tag))

  private def retrieveTrackFrom(tag: TaggerTag) = try tag.getFirst(TRACK) catch { case _ => "" }
}