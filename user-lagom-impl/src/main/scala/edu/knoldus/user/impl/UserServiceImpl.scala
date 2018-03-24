package edu.knoldus.user.impl

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.user.api.{UserData, UserService}

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl(implicit ec: ExecutionContext) extends UserService {


  val userList = new ListBuffer[UserData]


  override def createUser: ServiceCall[UserData, String] = ServiceCall { request =>

    val newUser = new UserData(request.id, request.name, request.salary)

    val user = userList.find(user => user.id == newUser.id)

    user match {
      case Some(_: UserData) => Future.successful("id already exist..")
      case _ => userList += newUser
        Future.successful("user added")
    }
  }

  override def getUser(id: Int): ServiceCall[NotUsed, UserData] = ServiceCall { request =>

    val user = userList.find(user => user.id == id)
    user match {
      case Some(user: UserData) => Future.successful(user)
      case None => Future.successful(throw new Exception("user not found"))
    }
  }

  override def deleteUser(id: Int): ServiceCall[NotUsed, Done] = ServiceCall { request =>

    val user = userList.find(user => user.id == id)
    userList --= user
    Future.successful(Done)
  }

  override def updateUser(id: Int): ServiceCall[UserData, String] = ServiceCall { request =>

    val user = userList.find(user => user.id == id)
    val result = user.getOrElse("user not found")
    if (result != "user not found") {
      userList --= user
      val newUser = UserData(request.id, request.name, request.salary)
      userList += newUser
      Future.successful(s"userId ${id} updated")
    } else {
      Future.successful(s"userId ${id} not found")
    }
  }
}
