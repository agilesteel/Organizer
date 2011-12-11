package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    setRootDirectoryForScalaWebCode()
    def sitemap(): SiteMap = generateSiteMap
    setSiteMap()
    showAjaxImage()
    hideAjaxImage()
    forceUTF8Request()
    enableHTML5Rendering()

    def setRootDirectoryForScalaWebCode() {
      LiftRules.addToPackages("de.htwgkonstanz.organizer.delivery.web")
    }

    def generateSiteMap = SiteMap(
      Menu.i("Home") / "index",
      Menu.i("Second Page") / "second")

    def setSiteMap() {
      LiftRules.setSiteMapFunc(() => sitemap())
    }

    def showAjaxImage() {
      LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    }

    def hideAjaxImage() {
      LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
    }

    def forceUTF8Request() {
      LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    }

    def enableHTML5Rendering() {
      LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))
    }
  }

}
