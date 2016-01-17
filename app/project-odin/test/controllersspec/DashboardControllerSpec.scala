package controllersspec

import org.specs2.mutable.Specification
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

/**
 * Created by Sa2 on 15/10/14.
 */
class DashboardControllerSpec extends Specification {

  "DashboardControllerTest" should {
    "ダッシュボードをレンダリング" in new WithApplication{
      val articleList = route(FakeRequest(GET, "/dashboard")).get

      status(articleList) must equalTo(OK)
      contentType(articleList) must beSome.which(_ == "text/html")
      contentAsString(articleList) must contain ("ダッシュボード")
    }
  }
}
