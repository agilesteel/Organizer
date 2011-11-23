package de.htwgkonstanz.organizer.delivery.tui

class OrganizerView(val controller: Controller) {
  def display() {
    presentGreeting()
    presentMenu()
  }

  private def presentGreeting() {
    present("Welcome to song organizer!")
    present("Hold on a sec, I am initializing the neccessary components...\n")
  }

  private def present(x: Any = "") {
    println(x)
  }

  private def presentMenu() {
    var c = controller

    while (true) {
      presentList(c.menu)
      val r = readLine
      r match {
        case "source" | "s" => {
          present("In which directory are your unorganized songs?")
          c = c.setSource(readLine)
        }
        case "target" | "t" => {
          present("Where should I organize your songs to?")
          c = c.setTarget(readLine)
        }
        case "parse" | "pa" => {
          if (c.areControllsEnabled) {
            present("Look what I've found")
            presentList(c.parse)
          } else
            Console.err.println("I beg your pardon?")
        }
        case "preview" | "p" =>
          if (c.areControllsEnabled) {
            present("This is how your new directory structure will look like:")
            presentList(c.preview)
          } else
            Console.err.println("I beg your pardon?")
        case "organize" | "o" => {
          if (c.areControllsEnabled) {

            present("This can take a while. You might wanna grab a snack...")
            c.organize()
          } else
            Console.err.println("I beg your pardon?")
        }
        case "start over" | "so" => c = c.startOver
        case "quit" | "q" | "exit" | "e" => return
        case unknownCommand => Console.err.println("I beg your pardon?")
      }
      presentList(c.status)
    }
  }

  private def presentList(list: scala.collection.GenTraversable[_]) {
    present(list mkString "\n")
  }
}