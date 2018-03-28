package edu.knoldus.user.api

import play.api.libs.json.{Format, Json}

case class UserData(id: Int, name: String, salary: Int)


object UserData {
  /**
    * Format for converting user messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[UserData] = Json.format[UserData]
}
