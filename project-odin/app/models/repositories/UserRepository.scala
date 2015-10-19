package models.repositories

import javax.inject.Inject

import com.google.inject.ImplementedBy
import models.dao.Tables._
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
 * Created by Sa2 on 15/09/21.
 */
@ImplementedBy(classOf[UserRepository])
trait UserRepositoryLike
  extends HasDatabaseConfigProvider[JdbcProfile] {
  def findByUserId(userId: String): Future[Option[UsersRow]]
}

class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends UserRepositoryLike {
  import driver.api._

  def findByUserId(userId: String): Future[Option[UsersRow]] = {
    db.run(Users.filter(_.userId === userId).result.headOption)
  }
}
