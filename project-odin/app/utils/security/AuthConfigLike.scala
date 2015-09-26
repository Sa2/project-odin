package utils.security

import models.services.UserService

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.{ClassTag, classTag}

import play.api.mvc.Results._
import play.api.mvc._

import controllers.routes

import jp.t2v.lab.play2.auth._
import viewmodels.UserViewModel


/**
 * Created by Sa2 on 15/09/21.
 */
trait AuthConfigLike extends AuthConfig {

  type UserId = String
  type User = UserViewModel
  type Authority = None.type // TODO role type

  val idTag: ClassTag[Id] = classTag[Id]
  val sessionTimeoutInSeconds: Int = 3600

  val userService: UserService


  def resolveUser(userId: UserId)(implicit ctx: ExecutionContext): Future[Option[UserViewModel]] = {
    userService.findByUserId(userId)
  }


}
