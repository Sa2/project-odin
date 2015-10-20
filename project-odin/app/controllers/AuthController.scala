package controllers

import forms.LoginForm
import jp.t2v.lab.play2.auth.LoginLogout
import play.filters.csrf.{AddCSRFToken, CSRFCheck}
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

  def index() = Action { implicit request =>
    Ok(views.html.auth.login(loginForm))
  }

  def login() = CSRFCheck {
    Action.async { implicit request =>
      loginForm.bindFromRequest.fold(
        formWithErrors => {
          Future.successful(BadRequest(views.html.auth.login(formWithErrors)))
        },
        form => {
          userService.authenticate(form).flatMap {
            user => user match {
              case Some(user) =>
                gotoLoginSucceeded(user.userId)
              case _ =>
                Future.successful(Unauthorized(views.html.auth.login(loginForm.fill(form)
                  .withGlobalError("message", Messages("login.fail")))))
            }
          }
        }
      )
    }
  }

  def logout() = CSRFCheck {
    Action.async { implicit request =>
      gotoLogoutSucceeded
    }
  }
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