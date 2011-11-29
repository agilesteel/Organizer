package de.htwgkonstanz.organizer.delivery.gui

import de.htwgkonstanz.organizer.delivery.tui._
import scala.swing._
import scala.collection.interfaces.SetMethods
import javax.swing.JFrame
import scala.swing.event._

case object ControllerRefreshed extends scala.swing.event.Event

class OrganizerView(private[this] var _controller: Controller) extends Frame with Publisher with NativeLookAndFeel {
  controller = _controller
  def controller = _controller
  def controller_=(c: Controller) {
    _controller = c
    publish(ControllerRefreshed)
  }

  listenTo(this, source, target)
  contents = new BoxPanel(Orientation.Vertical) {
    border = Swing.EmptyBorder(5, 5, 5, 5)
    contents += new Label("Source")
    contents += new FlowPanel {
      contents += source
      contents += new Button("Select")
    }
    contents += new Label("Target")
    contents += new FlowPanel {
      contents += target
      contents += new Button("Select")
    }
    contents += new TextArea
    contents += new FlowPanel {
      contents += parse
      contents += preview
      contents += organize
    }
  }

  lazy val source = new TextField(controller.source) {
    columns = 50
  }

  lazy val target = new TextField(controller.target) {
    columns = 50
  }

  lazy val parse = new Button("Parse") {
    enabled = controller.areControllsEnabled
  }
  
  lazy val preview = new Button("Preview") {
    enabled = controller.areControllsEnabled
  }
  
  lazy val organize = new Button("Organize") {
    enabled = controller.areControllsEnabled
  }

  reactions += {
    case EditDone(`source`) => controller = controller.setSource(`source`.text)
    case EditDone(`target`) => controller = controller.setTarget(`target`.text)
    case ControllerRefreshed => {
      parse.enabled = controller.areControllsEnabled
      preview.enabled = controller.areControllsEnabled
      organize.enabled = controller.areControllsEnabled
    }
  }

  peer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  centerOnScreen()
  pack()
  title = "Organizer"
  resizable = false
  visible = true
}

trait NativeLookAndFeel {
  import javax.swing._
  try UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
  catch { case _ => UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName) }
}