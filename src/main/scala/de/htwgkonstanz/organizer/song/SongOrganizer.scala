package de.htwgkonstanz.organizer.song

import de.htwgkonstanz.organizer._
import flac._
import mp3._
import io._
import FileSystem._

class SongOrganizer(val organizingStrategy: SongFile => String) {
  def parse(sourcePath: String): Seq[SongFile] = {
    val songFiles = traverse(sourcePath)
    val mp3Parser = new Mp3Parser(new Mp3Reader)
    val flacParser = new FlacParser(new FlacReader)

    songFiles map {
      case flac @ File(_, _, Extensions.FLAC) => flacParser.parse(flac)
      case mp3 @ File(_, _, Extensions.MP3) => mp3Parser.parse(mp3)
    }
  }

  private def traverse(sourcePath: String): Seq[String] = FileSystem.traverse(sourcePath)(Extensions.FLAC, Extensions.MP3)

  def organize(songFiles: Seq[SongFile]): Unit = {
    for ((SongFile(_, source), target) <- preview(songFiles))
      FileSystem.copy(source, target)
  }

  def preview(songFiles: Seq[SongFile]): Map[SongFile, String] = songFiles
    .map { songFile => songFile -> organizingStrategy(songFile) }
    .toMap
}