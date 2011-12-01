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

  listenTo(this, source, target, parse, preview, organize)
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
    contents += new ScrollPane(sourceResults)
    contents += new ScrollPane(targetResults)
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

  lazy val sourceResults = new TextArea
  lazy val targetResults = new TextArea

  reactions += {
    case EditDone(`source`) => controller = controller.setSource(`source`.text)
    case EditDone(`target`) => controller = controller.setTarget(`target`.text)
    case ButtonClicked(`parse`) => clearResults(); controller.parse.fold(presentError, presentList(sourceResults))
    case ButtonClicked(`preview`) => clearResults(); controller.preview.fold(presentError, presentMap)
    case ButtonClicked(`organize`) => clearResults(); controller.organize.fold(presentError, Unit => ())
    case ControllerRefreshed => enableButtons()
  }

  private def enableButtons() {
    parse.enabled = controller.areControllsEnabled
    preview.enabled = controller.areControllsEnabled
    organize.enabled = controller.areControllsEnabled
  }

  private def clearResults() {
    sourceResults.text = ""
    targetResults.text = ""
  }

  private def presentError(error: String) {
    presentLine(sourceResults)(error)
  }

  private def presentLine(area: TextArea)(x: Any = "") {
    presentSingleLine(area)(x + "\n")
  }

  private def presentSingleLine(area: TextArea)(x: Any = "") {
    area.text += x.toString
  }

  private def presentList(area: TextArea)(list: scala.collection.GenTraversable[_]) {
    presentLine(area)(list mkString "\n")
  }

  private def presentMap(map: scala.collection.GenMap[_, _]) {
    for ((from, to) <- map) {
      presentLine(sourceResults)(from)
      presentLine(targetResults)(to)
    }
  }

  peer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  centerOnScreen()
  pack()
  maximize()
  title = "Organizer"
  visible = true
}

trait NativeLookAndFeel {
  import javax.swing._
  try UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
  catch { case _ => UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName) }
}