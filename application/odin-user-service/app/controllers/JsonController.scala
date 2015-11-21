package controllers

import javax.inject.Inject

import org.joda.time.DateTime
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

object JsonController {
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

class JsonController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi, val userService: UserService)
  extends Controller with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {
  import JsonController._

  /**
   * ユーザー取得
   */
  def getUser(id: Int) = Action.async { implicit rs =>
    // IDの昇順にすべてのユーザ情報を取得
    val user = userService.getUser(id)
    user.map { user =>
      // ユーザの一覧をJSONで返す
      Ok(Json.obj("user" -> user))
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
        userService.createUser(form)
        Ok(Json.obj("result" -> "success"))
      }

    }.recoverTotal { e =>
      Future {
        BadRequest(Json.obj("result" ->"failure", "error" -> JsError.toJson(e)))
      }
    }
  }

  /**
   * ユーザ更新
   */
  def update(id: Int) = TODO

  /**
   * ユーザ削除
   */
  def remove(id: Int) = TODO

  /**
    * ユーザをロックする
    */
  def lock(id: Int) = TODO
}