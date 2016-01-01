package models.services

import java.util.UUID
import javax.inject.Inject

import controllers.UserApiController.UserForm
import exception.UserServiceException
import models.dao.Tables._
import org.apache.commons.codec.digest.DigestUtils
import org.joda.time.DateTime
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.i18n.{I18nSupport, MessagesApi}
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._


import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * Created by Sa2 on 2015/11/02.
 */
class UserService @Inject()(val dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi) extends HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {
  import UserService._

  /**
    * IDからユーザーを取得
    *
    * @param id ID
    * @return
    */
  def getUserById(id: Int): Future[UsersRow] = {
    db.run(Users.filter(t => t.id === id).result.head)
  }

  /**
    * ユーザーIDからユーザーを取得
    *
    * @param userId ユーザーID
    * @return
    */
  def getUserByUserId(userId: String): Future[UsersRow] = {
    db.run(Users.filter(t => t.userId === userId).result.head)
  }

  /**
    * ユーザーIDが使用できるか確認する
    *
    * @param userId
    * @return
    */
  def isAvailableUserId(userId: String): Boolean = {
    val user: Future[UsersRow] = getUserByUserId(userId)

    try {
      val result = Await.result(user, Duration.Inf)
      return false
    } catch {
      case e:RuntimeException => return true
    }
  }

  /**
   * ユーザー一覧を取得
    *
    * @return ユーザー一覧
   */
  def getUserList: Future[Seq[UsersRow]] = {
    db.run(Users.sortBy(t => t.id).result)
  }

  /**
    * ユーザーを作成
    *
    * @param form ユーザーフォーム
    */
  def createUser(form: UserForm) = {
    val passwordSalt = createPasswordSalt
    val hashedPassword = hashAndStretch(form.password, passwordSalt, STRETCH_LOOP_COUNT)
    val user = UsersRow(0, form.userId, hashedPassword, passwordSalt, form.name, form.roleId, form.isLock, DateTime.now(), DateTime.now())
    db.run(Users += user)
  }

  /**
    * ユーザー情報を更新
    *
    * @param form ユーザーフォーム
    * @return
    */
  def updateUser(form: UserForm) = {
    // 既存ユーザー情報を取得してformと組み合わせた情報をupdateする
    getUserById(form.id).map { currentUser =>
      val newHashedPassword = hashAndStretch(form.password, currentUser.passwordSalt, STRETCH_LOOP_COUNT)
      val editUser = UsersRow(form.id, form.userId, newHashedPassword, currentUser.passwordSalt, form.name, form.roleId, form.isLock, currentUser.registerDate, DateTime.now())
      db.run(Users.filter(t => t.id === editUser.id.bind).update(editUser))
    }
  }

  /**
    * ユーザー情報を削除
    *
    * @param id ID
    * @return
    */
  def removeUser(id: Int) = {
    // TODO : FKを張っているので子になるテーブルから関連データを削除する処理が必要
    db.run(Users.filter(t => t.id === id.bind).delete)
  }

  /**
    * ユーザーをロックする
    * @param id
    * @return
    */
  def lockUser(id: Int) = {
    getUserById(id).map { targetUser =>
      val lockedUser = UsersRow(targetUser.id, targetUser.userId, targetUser.password, targetUser.passwordSalt, targetUser.name, targetUser.roleId, true, targetUser.registerDate, DateTime.now())
      db.run(Users.filter(t => t.id === targetUser.id.bind).update(targetUser))
    }
  }
}


object UserService {
  val STRETCH_LOOP_COUNT = 1000

  def hashAndStretch(plain: String, salt: String, loopCnt: Int): String = {
    var hashed: String = ""
    (1 to STRETCH_LOOP_COUNT).foreach(i =>
      hashed = DigestUtils.sha256Hex(hashed + plain + salt)
    )
    hashed
  }

  def createPasswordSalt: String = {
    DigestUtils.sha256Hex(UUID.randomUUID().toString())
  }
}