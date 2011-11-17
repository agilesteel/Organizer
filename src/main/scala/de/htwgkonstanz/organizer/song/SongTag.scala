package de.htwgkonstanz.organizer.song

final case class SongTag(
  title: String = "",
  artist: String = "",
  album: String = "",
  year: String = "",
  trackNumber: String = "")

final case class Mp3Tag(id3v1: SongTag, id3v2: SongTag)