package controllers

import org.joda.time.DateTime
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.db.slick._
import slick.driver.JdbcProfile
import models.Tables._
import javax.inject.Inject
import slick.driver.MySQLDriver.api._

/**
 * Created by Sa2 on 15/07/19.
 */

object ArticleController {
  case class ArticleForm(id: Int, title: String, body: Option[String], viewCount: Long, isHide: Boolean, postedUserId: Option[Int], postDate: Option[DateTime], updateDate: Option[DateTime])
  // formから送信されたデータ ⇔ ケースクラスの変換を行う
  val articleForm = Form(
    mapping(
      "id"        -> number,
      "title"     -> nonEmptyText(maxLength = 64),
      "body" -> optional(text),
      "viewCount" -> longNumber,
      "isHide" -> boolean,
      "postedUserId" -> optional(number),
      "postDate" -> optional(jodaDate),
      "updateDate" -> optional(jodaDate)
    )(ArticleForm.apply)(ArticleForm.unapply)
  )
}

class ArticleController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi) extends Controller with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  /**
   * 一覧表示
   */
  def list = Action.async { implicit rs =>
    // IDの昇順にすべてのユーザ情報を取得
    db.run(Articles.sortBy(t => t.id).result).map { articles =>
      // 一覧画面を表示
      Ok(views.html.article.list(articles))
    }
  }

  /**
   * 編集画面表示
   */
  def edit(id: Option[Long]) = TODO

  /**
   * 登録実行
   */
  def create = TODO

  /**
   * 更新実行
   */
  def update = TODO

  /**
   * 削除実行
   */
  def remove(id: Long) = TODO

}
