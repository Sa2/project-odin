package controllersspec

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Created by Sa2 on 15/08/30.
 */
@RunWith(classOf[JUnitRunner])
class ArticleControllerSpec extends Specification {

  "ArticleControllerTest" should {
    "記事一覧ページをレンダリング" in new WithApplication{
      val articleList = route(FakeRequest(GET, "/article/list")).get

      status(articleList) must equalTo(OK)
      contentType(articleList) must beSome.which(_ == "text/html")
      contentAsString(articleList) must contain ("記事一覧")
    }

    "記事編集ページをレンダリング" in new WithApplication{
      val articleEdit = route(FakeRequest(GET, "/article/edit?id=1")).get

      status(articleEdit) must equalTo(OK)
      contentType(articleEdit) must beSome.which(_ == "text/html")
      contentAsString(articleEdit) must contain ("記事編集")
    }
  }

}
