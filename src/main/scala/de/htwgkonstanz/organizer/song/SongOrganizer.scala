package de.htwgkonstanz.organizer.song

import de.htwgkonstanz.organizer._
import flac._
import mp3._
import io._
import FileSystem._

class SongOrganizer {
  def traverse(sourcePath: String): Seq[String] = FileSystem.traverse(sourcePath)(Extensions.FLAC, Extensions.MP3)

  def parse(songFiles: Seq[String]): Seq[SongFile] = {
    val mp3Parser = new Mp3Parser(new Mp3Reader)
    val flacParser = new FlacParser(new FlacReader)

    songFiles map {
      case flac @ File(_, _, Extensions.FLAC) => flacParser.parse(flac)
      case mp3 @ File(_, _, Extensions.MP3) => mp3Parser.parse(mp3)
    }
  }

  def preview(songFiles: Seq[SongFile], organizingStrategy: SongFile => String): Map[SongFile, String] = songFiles.map {
    case songFile: SongFile => songFile -> organizingStrategy(songFile)
  }.toMap

  def organize(map: Map[SongFile, String]): Unit = {
    for ((SongFile(_, source), target) <- map)
      FileSystem.copy(source, target)
  }
}