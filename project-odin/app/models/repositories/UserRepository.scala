package models.repositories

import javax.inject.Inject

import models.dao.Tables
import models.dao.Tables._
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
 * Created by Sa2 on 15/09/21.
 */
class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import driver.api._

  def findByUserId(userId: String): Future[Option[UsersRow]] = {
    db.run(Users.filter(_.userId === userId).result.headOption)
  }
}
