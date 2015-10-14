package controllers

import play.api.mvc._

/**
 * Created by Sa2 on 15/10/14.
 */
class DashboardController extends Controller {

  def index = Action {
    Ok(views.html.dashboard())
  }

}
