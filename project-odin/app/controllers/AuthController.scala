package controllers

import forms.LoginForm
import jp.t2v.lab.play2.auth.LoginLogout
import utils.security.AuthConfigLike
import utils.validation.CustomConstraints

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.mvc._

import com.google.inject.Inject
import models.services.UserServiceLike

/**
 * Created by Sa2 on 15/10/19.
 */
class AuthController @Inject()(val userService: UserServiceLike) extends Controller with LoginLogout with AuthConfigLike {
  import AuthController._

  def index() = TODO
  def login() = TODO
  def logout() = TODO
}

object AuthController {
  val loginForm = Form(
    mapping(
      "userId" -> text.verifying(Messages("validation.error.required"), {!_.isEmpty})
        .verifying(CustomConstraints.userId),
      "password" -> text.verifying(Messages("validation.error.required"), {!_.isEmpty})
    )(LoginForm.apply)(LoginForm.unapply)
  )
}