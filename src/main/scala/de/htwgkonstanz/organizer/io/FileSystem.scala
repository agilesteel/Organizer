package de.htwgkonstanz.organizer.io

import java.nio.file._

object FileSystem {
  val separator: String = java.io.File.separator
  val invalidCharacters: String = """\\/:"*?<>|"""

  object File {
    def apply(location: String, name: String, extension: String): String = {
      Paths.get(location + separator + name + "." + extension).toString
    }

    def apply(locationParts: Seq[String], name: String, extension: String): String = {
      Paths.get(((locationParts :+ name) mkString separator) + "." + extension).toString
    }

    def unapply(filePath: String): Option[(String, String, String)] = {
      if (filePath eq null) None else {
        val path = Paths.get(filePath)
        val location = path.getParent.toString
        val nameWithExtension = path.getFileName.toString
        val nameWithExtensionParts = nameWithExtension.split("""\.""")
        val name = nameWithExtensionParts.head
        val extension = nameWithExtensionParts.last
        if (name == extension) None else
          Some(location, name, extension)
      }
    }
  }

  def exists(filePath: String): Boolean = new java.io.File(filePath).exists

  // test if path exists
  def traverse(path: String)(implicit extensions: String*): List[String] = {
    def getFiles(directory: java.io.File): List[java.io.File] = {
      directory.setReadOnly()
      val artifacts = directory.listFiles.toList
      val (directories, currentFiles) = artifacts partition { _.isDirectory }
      val childrensFiles = directories flatMap getFiles
      currentFiles ::: childrensFiles
    }

    val folder = new java.io.File(path)
    val files = getFiles(folder)
    val filteredFiles = filterExtensions(files, extensions)
    filteredFiles map { _.toString }
  }
  
  private def filterExtensions(files: List[java.io.File], extensions: Seq[String]) =
    files filter { file => extensions exists { extension => file.toString.endsWith(extension) } }

  def delete(filePath: String): Unit = new java.io.File(filePath).delete()

  def copy(source: String, target: String): Unit = {
    val File(targetLocation, _, _) = target
    if (!exists(targetLocation))
      Files.createDirectories(Paths.get(targetLocation))
    Files.copy(Paths.get(source), Paths.get(target), StandardCopyOption.REPLACE_EXISTING)
  }
  
  val desktop = System.getProperty("user.home") + "/Desktop"
}