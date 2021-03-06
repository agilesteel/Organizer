package com.github.agilesteel.organizer.song

import com.github.agilesteel.organizer.io.FileSystem._

class SongFileOrganizingStrategy(val targetDirectory: String) extends (SongFile => String) {
  def apply(songFile: SongFile): String = {
    import songFile.song.{
      title => optionalSongTitle,
      artist => optionalArtist,
      album => optionalAlbum,
      year => optionalYear
    }

    val File(_, fileName, fileExtension) = songFile.filePath

    def generateFilePath(locationParts: Seq[String], name: String) = File(locationParts, name, fileExtension)

    def artistOrYearOrAlbumAreNotSpecified = List(optionalArtist, optionalYear, optionalAlbum) contains None
    def artistHasLessThanOneCharacter = optionalArtist.get.isEmpty
    def artistStartsWithInvalidCharacter = invalidCharacters contains optionalArtist.get.head

    if (optionalSongTitle == None)
      generateFilePath(unassignedDirectoryParts, fileName)
    else if (artistOrYearOrAlbumAreNotSpecified || artistHasLessThanOneCharacter || artistStartsWithInvalidCharacter)
      generateFilePath(unassignedDirectoryParts, handleInvalidCharacters(optionalSongTitle.get))
    else {
      val values = List(optionalSongTitle, optionalArtist, optionalAlbum, optionalYear).flatten map handleInvalidCharacters
      val List(title, artist, album, year) = values
      val locationParts = List(targetDirectory, artist.head.toString, artist, "(" + year + ") - " + album)
      val name = artist + " - " + title
      generateFilePath(locationParts, name)
    }
  }

  private def handleInvalidCharacters(property: String) = {
    val propertyWithoutTrailingWhiteSpaces = removeTrailingWhiteSpaces(property)
    val pattern = "[" + invalidCharacters + "]+"
    val propertyWithCheckedStartAndEnd = checkStartAndEndConstraints(propertyWithoutTrailingWhiteSpaces, pattern)
    propertyWithCheckedStartAndEnd.replaceAll(pattern, " ")
  }

  private def removeTrailingWhiteSpaces(property: String) = property.stripPrefix(" ").stripSuffix(" ")

  private def checkStartAndEndConstraints(property: String, pattern: String) = {
    def propertyNeitherStartsNorEndsWith(c: String) = !property.startsWith(c) && !property.endsWith(c)
    if (invalidCharacters map { _.toString } forall propertyNeitherStartsNorEndsWith)
      property
    else
      property.replaceAll(pattern, "")
  }

  val unassignedDirectoryParts: List[String] = List(targetDirectory, "Unassigned")
}