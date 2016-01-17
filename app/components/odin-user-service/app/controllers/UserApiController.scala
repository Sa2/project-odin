package controllers

import javax.inject.Inject

import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile
import models.dao.Tables._
import models.services._

import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
 * Created by Sa2 on 2015/11/02.
 */

object UserApiController {
  case class UserForm(id: Int, userId: String, password: String, name: String, roleId: Int, isLock: Boolean)

    // UsersRowをJSONに変換するためのWritesを定義
    implicit val usersRowWritesFormat = new Writes[UsersRow]{
      def writes(user: UsersRow): JsValue = {
        Json.obj(
          "id"            -> user.id,
          "userId"        -> user.userId,
          "password"      -> user.password,
          "name"          -> user.name,
          "roleId"        -> user.roleId,
          "isLock"        -> user.isLock,
          "registerDate"  -> user.registerDate,
          "updateDate"    -> user.updateDate
        )
      }
  }

  implicit val userFormFormat = Json.format[UserForm]
}

class UserApiController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi, val userService: UserService)
  extends Controller with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {
  import UserApiController._

  /**
   * IDからユーザー情報取得
   */
  def getUserById(id: Int) = Action.async { implicit rs =>
    // IDの昇順にすべてのユーザ情報を取得
    val user = userService.getUserById(id)
    user.map { user =>
      // ユーザの一覧をJSONで返す
      Ok(Json.toJson(user))
    }
  }

  /**
    * ユーザーIDからユーザー情報取得
    */
  def getUserByUserId(userId: String) = Action.async { implicit rs =>
    // IDの昇順にすべてのユーザ情報を取得
    val user = userService.getUserByUserId(userId)
    user.map { user =>
      // ユーザの一覧をJSONで返す
      Ok(Json.toJson(user))
    }
  }

  /**
   * ユーザー一覧取得
   */
  def getUserList = Action.async { implicit rs =>
    // IDの昇順にすべてのユーザ情報を取得
    val users = userService.getUserList
    users.map { users =>
      // ユーザの一覧をJSONで返す
      Ok(Json.obj("users" -> users))
    }
  }

  /**
   * ユーザ登録
   */
  def create = Action.async(parse.json) { implicit rs =>
    rs.body.validate[UserForm].map { form =>
      Future {
        if (userService.isAvailableUserId(form.userId)) {
          userService.createUser(form)
          Ok(Json.obj("result" -> "success"))
        } else {
          BadRequest(Json.obj("result" -> "failure", "error" -> "duplicate key"))
        }
      }
    }.recoverTotal { e =>
      Future {
        BadRequest(Json.obj("result" -> "failure", "error" -> JsError.toJson(e)))
      }
    }
  }

  /**
   * ユーザ更新
   */
  def update = Action.async(parse.json) { implicit rs =>
    rs.body.validate[UserForm].map { form =>
      Future {
        userService.updateUser(form)
        Ok(Json.obj("result" -> "success"))
      }
    }.recoverTotal { e =>
      Future {
        BadRequest(Json.obj("result" ->"failure", "error" -> JsError.toJson(e)))
      }
    }
  }

  /**
   * ユーザ削除
   */
  def remove(id: Int) = Action.async { implicit rs =>
    Future {
      userService.removeUser(id)
      Ok(Json.obj("result" -> "success"))
    }
  }

  /**
    * ユーザロック
    */
  def lock(id: Int) = Action.async { implicit rs =>
    Future {
      userService.lockUser(id)
      Ok(Json.obj("result" -> "success"))
    }
  }
}