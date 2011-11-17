package de.htwgkonstanz.organizer.delivery.tui

abstract class Controller {
  def menu: Seq[String]
  def quit(): Unit
  def status: String
  def refresh(): Unit
  def setSourceDirectory(path: String): Unit
  def setTargetDirectory(path: String): Unit
  def preview: Seq[String]
  def organize(): Unit
}

class SimpleController extends Controller {
  def menu = List("Here are your options:")

  def quit() {
    System.exit(0)
  }

  def status = "This is a sample status report"

  def refresh() {}

  def setSourceDirectory(path: String) {}
  def setTargetDirectory(path: String) {}

  def preview = Nil
  def organize() {}

}

object View extends App {
  presentGreeting()
  val controller: Controller = new SimpleController
  import controller._

  while (true) {
    presentList(menu)
    readLine match {
      case "source" | "s" => {
        present("In which directory are your songs?")
        setSourceDirectory(readLine)
      }
      case "target" | "t" => {
        present("Where should I organize your songs to?")
        setTargetDirectory(readLine)
      }
      case "preview" | "p" =>
        present("This is how your new directory structure will look like:")
        presentList(preview)
      case "organize" | "o" => {
        present("This can take a while. You might wanna grab a snack...")
        organize()
      }
      case "refresh" | "r" => refresh()
      case "quit" | "q" | "exit" | "e" => quit()
      case unknownCommand => Console.err.println("I beg your pardon?")
    }
    present(status)
  }

  def presentGreeting() {
    present("Welcome to song organizer!")
    present("Hold on a sec, I am initializing the neccessary components...")
    present()
  }

  def present(x: Any = "") {
    println(x)
  }

  def presentList(list: scala.collection.GenTraversable[_]) {
    present(list mkString "\n")
  }

}