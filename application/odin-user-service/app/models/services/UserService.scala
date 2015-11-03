package models.services

import java.util.UUID
import javax.inject.Inject

import models.dao.Tables
import models.dao.Tables._
import org.apache.commons.codec.digest.DigestUtils
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.i18n.{I18nSupport, MessagesApi}
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
 * Created by Sa2 on 2015/11/02.
 */
class UserService @Inject()(val dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi) extends HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {
  import UserService._

  def getUser(id: Int): Future[UsersRow] = {
    db.run(Users.filter(t => t.id === id).result.head)
  }

  /**
   * ユーザー一覧を取得
   * @return ユーザー一覧
   */
  def getUserList: Future[Seq[UsersRow]] = {
    db.run(Users.sortBy(t => t.id).result)
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