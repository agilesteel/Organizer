package com.github.agilesteel.organizer.io

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

  def traverse(path: String)(implicit extensions: String*): List[String] = {
    val visitor = new FileVisitor(path, extensions)
    Files.walkFileTree(Paths.get(path), visitor)
    visitor.paths
  }

  private class FileVisitor(path: String, extensions: Seq[String]) extends SimpleFileVisitor[Path] {
    var paths = List[String]()
    override def visitFileFailed(file: Path, exc: java.io.IOException): FileVisitResult = FileVisitResult.CONTINUE
    override def visitFile(filePath: Path, attrs: attribute.BasicFileAttributes) = {
      filePath.toString match {
        case fileString @ File(_, _, extension) if extensions.contains(extension) => paths ::= fileString
        case _ =>
      }
      FileVisitResult.CONTINUE
    }
  }

  def delete(filePath: String): Unit = new java.io.File(filePath).delete()

  def copy(source: String, target: String): Unit = {
    val File(targetLocation, _, _) = target
    if (!exists(targetLocation))
      Files.createDirectories(Paths.get(targetLocation))
    Files.copy(Paths.get(source), Paths.get(target), StandardCopyOption.REPLACE_EXISTING)
  }

  val desktop = System.getProperty("user.home").replace("""\""", "/") + "/Desktop"
}