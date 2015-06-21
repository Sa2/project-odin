package controllers

import play.api.mvc._

/**
 * Created by Sa2 on 15/06/21.
 */
object ArticleController extends Controller {

  /**
   * 検索表示
   */
  def search(word: String) = TODO

  /**
   * 一覧表示
   */
  def list = TODO

  /**
   * 登録・編集画面表示
   */
  def edit(id: Option[Long]) = TODO

  /**
   * 登録実行
   */
  def create = TODO

  /**
   * 更新処理
   */
  def update = TODO

  /**
   * 削除実行
   */
  def remove(id: Long) = TODO
}
