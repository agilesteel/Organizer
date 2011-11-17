package de.htwgkonstanz.organizer.song

final case class Song(
  title: Option[String] = None,
  artist: Option[String] = None,
  album: Option[String] = None,
  year: Option[String] = None,
  track: Option[String] = None) {

  override def toString = List(title, artist, album, year, track)
    .map { _.getOrElse("") }
    .mkString("Song(", ",", ")")
}