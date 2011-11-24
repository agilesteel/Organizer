package de.htwgkonstanz.organizer.song

import de.htwgkonstanz.organizer._
import flac._
import mp3._
import io._
import FileSystem._
import SongOrganizer._

object SongOrganizer {
  object DirectoryNotFound extends Exception("Directory not found")
}

class SongOrganizer(val organizingStrategy: SongFile => String) {
  def parse(sourcePath: String): Seq[SongFile] = {
    throwIfSourcePathDoesNotExist(sourcePath)
    val songFiles = traverse(sourcePath)
    val mp3Parser = new Mp3Parser(new Mp3Reader)
    val flacParser = new FlacParser(new FlacReader)

    songFiles map {
      case flac @ File(_, _, Extensions.FLAC) => flacParser.parse(flac)
      case mp3 @ File(_, _, Extensions.MP3) => mp3Parser.parse(mp3)
    }
  }

  private def throwIfSourcePathDoesNotExist(sourcePath: String) {
    if (!exists(sourcePath)) throw DirectoryNotFound
  }

  private def traverse(sourcePath: String): Seq[String] = FileSystem.traverse(sourcePath)(Extensions.FLAC, Extensions.MP3)

  def preview(sourcePath: String): Map[String, String] = parse(sourcePath)
    .map { songFile => songFile.filePath -> organizingStrategy(songFile) }
    .toMap

  def organize(sourcePath: String): Unit = {
    for ((source, target) <- preview(sourcePath))
      copy(source, target)
  }
}