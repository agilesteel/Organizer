package com.github.agilesteel.organizer.song

abstract class SongParser[TagType] {
  protected val reader: SongReader[TagType]
  protected def fallBack(tag: TagType)(property: SongTag => String): Option[String]
  
  final def parse(filePath: String): SongFile = {
    val tag = reader.read(filePath)

    SongFile(
      Song(
        fallBack(tag) { _.title },
        fallBack(tag) { _.artist },
        fallBack(tag) { _.album },
        fallBack(tag) { _.year },
        fallBack(tag) { _.trackNumber }),
      filePath)
  }
}