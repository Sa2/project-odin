package controllers

import javax.inject.Inject

import play.api.mvc._

import scala.concurrent._
import scala.concurrent.duration.Duration

import models.services.HealthCheckService

/**
 * Created by Sa2 on 15/08/26.
 */
class AppStatusController @Inject()(val healthCheckService: HealthCheckService) extends Controller {

  def status = Action {
    val dbCheckResult = Await.result(healthCheckService.getDbAccessStatus, Duration.Inf)
    Ok(views.html.appinfo.status(dbCheckResult))
  }

}
