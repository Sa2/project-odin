package controllers

import org.joda.time.DateTime
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.db.slick._
import slick.driver.JdbcProfile
import models.dao.Tables._
import javax.inject.Inject
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future

/**
 * Created by Sa2 on 15/07/19.
 */

object ArticleController {
  case class ArticleForm(id: Int, title: String, body: Option[String], viewCount: Long, isHide: Boolean, postedUserId: Option[Int], postDate: DateTime, updateDate: DateTime)
  // formから送信されたデータ ⇔ ケースクラスの変換を行う
  val articleForm = Form(
    mapping(
      "id"        -> number,
      "title"     -> nonEmptyText(maxLength = 64),
      "body" -> optional(text),
      "viewCount" -> longNumber,
      "isHide" -> boolean,
      "postedUserId" -> optional(number),
      "postDate" -> jodaDate,
      "updateDate" -> jodaDate
    )(ArticleForm.apply)(ArticleForm.unapply)
  )
}

class ArticleController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi) extends Controller with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  // コンパニオンオブジェクトに定義したFormを参照するためにimport
  import ArticleController._

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
  def edit(id: Option[Int]) = Action.async { implicit rs =>
    // リクエストパラメータにIDが存在する場合
    val form = if(id.isDefined) {
      db.run(Articles.filter(t => t.id === id.get.bind).result.head).map { article =>
        articleForm.fill(ArticleForm(article.id, article.title, article.body, article.viewCount, article.isHide, article.postedUserId, article.postDate, article.updateDate))
      }
    } else {
      Future { articleForm }
    }

    // 投稿者IDからユーザー情報を参照
    form.flatMap { form =>
      db.run(Users.filter(t => t.id === form.value.get.postedUserId).result.head).map { user =>
        Ok(views.html.article.edit(form, user))
      }
    }
  }

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
