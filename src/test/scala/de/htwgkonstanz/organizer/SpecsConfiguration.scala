package de.htwgkonstanz.organizer

import org.scalatest._
import org.scalatest.matchers._

trait SpecsConfiguration extends FeatureSpec with GivenWhenThen with ShouldMatchers with BeforeAndAfterEach {
  protected def prettyPrint(collection: scala.collection.GenTraversableOnce[_]) = {
    val systemLineseparator = System.getProperty("line.separator")
    val separator = systemLineseparator + "\t\t"
    collection mkString (separator, separator, "")
  }
}