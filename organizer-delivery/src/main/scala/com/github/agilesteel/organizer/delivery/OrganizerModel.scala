package com.github.agilesteel.organizer.delivery

import com.github.agilesteel.organizer.song._

class OrganizerModel extends Model {
  def parse(source: String, target: String): Seq[SongFile] = {
    organizer(target).parse(source)
  }

  def preview(source: String, target: String): Map[String, String] = {
    organizer(target).preview(source)
  }

  def organize(source: String, target: String): Unit = {
    organizer(target).organize(source)
  }

  private def organizer(target: String) = new SongOrganizer(new SongFileOrganizingStrategy(target))
}