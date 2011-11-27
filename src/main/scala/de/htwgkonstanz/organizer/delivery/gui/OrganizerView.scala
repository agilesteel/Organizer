package de.htwgkonstanz.organizer.delivery.gui

import de.htwgkonstanz.organizer.delivery.tui._
import scala.swing._
import scala.collection.interfaces.SetMethods

class OrganizerView(var controller: Controller) {
  def display = new MainFrame {
    title = "Organizer"
    setLookAndFeel()
    resizable = false
    contents = new BoxPanel(Orientation.Vertical) {
      border = Swing.EmptyBorder(5, 5, 5, 5)
      contents += new Label("Source")
      contents += new FlowPanel {
        contents += new TextField((controller.asInstanceOf[OrganizerController]).source) {
          columns = 50
        }
        contents += new Button {
          action = Action("Select") {
            val chooser = new FileChooser
            
            chooser.showOpenDialog(this) match {
              case FileChooser.Result.Approve =>
              case _ =>
            }
          }
        }
      }
      contents += new Label("Target")
      contents += new FlowPanel {
        contents += new TextField((controller.asInstanceOf[OrganizerController]).target) {
          columns = 50
        }
        contents += new Button {
          action = Action("Select") {
            val chooser = new FileChooser
            chooser.showOpenDialog(this) match {
              case FileChooser.Result.Approve =>
              case _ =>
            }
          }
        }
      }
      contents += new TextArea
      contents += new FlowPanel {
        contents += new Button("Parse")
        contents += new Button("Preview")
        contents += new Button("Organize")
      }
    }
  }

  private def setLookAndFeel() {
    import javax.swing._
    try UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
    catch { case _ => UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName) }
  }
}