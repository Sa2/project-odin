package models.services

import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import models.dao.Tables._

import scala.concurrent.Future

/**
 * Created by Sa2 on 15/08/25.
 */
class HealthCheckService @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends BaseService with HasDatabaseConfigProvider[JdbcProfile] {

  def getDbAccessStatus: Future[Boolean] = {
    val healthCheckNum: Int = 1

    val result: Future[Boolean] = {
      val record = getDbAccessCheckRecord(healthCheckNum)

      record.map { status =>
        if (status.nonEmpty) {
          true
        } else {
          false
        }
      }
    }

    result
  }

  private def getDbAccessCheckRecord(recordId: Int): Future[Option[DbConnectionStatusRow]] = {
    db.run(DbConnectionStatus.filter(t => t.id === recordId.bind).result.headOption)
  }

}