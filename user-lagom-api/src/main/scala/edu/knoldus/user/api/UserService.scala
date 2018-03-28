package edu.knoldus.user.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait UserService extends Service {


  def createUser(): ServiceCall[UserData, String]

  def getUser(id: Int): ServiceCall[NotUsed, UserData]

  def deleteUser(id: Int): ServiceCall[NotUsed, Done]

  def updateUser(id: Int): ServiceCall[UserData, String]


  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("user-lagom")
        .withCalls(
          restCall(Method.POST, "/api/createUser", createUser _),
          restCall(Method.DELETE, "/api/deleteUser/:id", deleteUser _),
          restCall(Method.GET, "/api/getUser/:id", getUser _),
          restCall(Method.PUT, "/api/updateUser/:id", updateUser _)
        )
        .withAutoAcl(true)

  }
}