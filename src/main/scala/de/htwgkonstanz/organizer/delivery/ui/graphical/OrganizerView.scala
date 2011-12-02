package de.htwgkonstanz.organizer.delivery.ui.graphical

import scala.swing._
import scala.collection.interfaces.SetMethods
import javax.swing.JFrame
import scala.swing.event._
import BorderPanel.Position._
import de.htwgkonstanz.organizer.delivery.ui.Controller

case object ControllerRefreshed extends scala.swing.event.Event

class OrganizerView(private[this] var _controller: Controller) extends Frame with Publisher with NativeLookAndFeel {
  controller = _controller
  def controller = _controller
  def controller_=(c: Controller) {
    _controller = c
    publish(ControllerRefreshed)
  }

  listenTo(this, source, target, parse, preview, organize)
  contents = new BorderPanel {
    border = Swing.EmptyBorder(5, 5, 5, 5)
    add(inputControlls, North)
    add(outputArea, Center)
    add(outputControlls, South)
  }

  def inputControlls = new BorderPanel {
    border = Swing.EmptyBorder(5, 5, 5, 5)
    add(new BoxPanel(Orientation.Vertical) {
      contents += new Label(" ")
      contents += browse(source)
      contents += new Label(" ")
      contents += browse(target)
    }, East)

    def browse(field: TextField) = Button("Browse") {
      val chooser = new FileChooser
      chooser.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
      chooser.showOpenDialog(this) match {
        case FileChooser.Result.Approve => {
          val selected = chooser.selectedFile
          field.text = selected.getAbsolutePath
        }
        case _ =>
      }
    }

    add(new BoxPanel(Orientation.Vertical) {
      contents += new Label("Source")
      contents += source
      contents += new Label("Target")
      contents += target
    }, Center)
  }

  def outputArea = new BoxPanel(Orientation.Vertical) {
    border = Swing.EmptyBorder(5, 5, 5, 5)
    contents += new ScrollPane(sourceResults)
    contents += new ScrollPane(targetResults)
  }

  def outputControlls = new FlowPanel {
    contents += parse
    contents += preview
    contents += organize
  }

  lazy val source = new TextField(controller.source) {
    tooltip = "Source"
  }
  lazy val target = new TextField(controller.target) {
    tooltip = "Target"
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
    case ValueChanged(`source`) => controller = controller.setSource(`source`.text)
    case ValueChanged(`target`) => controller = controller.setTarget(`target`.text)
    case ButtonClicked(`parse`) => clearResults(); controller.parse.fold(presentError, presentList(sourceResults))
    case ButtonClicked(`preview`) => clearResults(); controller.preview.fold(presentError, presentMap)
    case ButtonClicked(`organize`) => clearResults(); controller.organize.fold(presentError, Unit => ())
    case ControllerRefreshed => refreshButtons()
  }

  private def refreshButtons() {
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

  private def presentMap(map: Map[String, String]) {
    val keys = map.keys.toList sortWith { _ < _ }
    keys foreach presentLine(sourceResults)

    val values = map.values.toList sortWith { _ < _ }
    values foreach presentLine(targetResults)
  }

  minimumSize = new Dimension(800, 600)
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