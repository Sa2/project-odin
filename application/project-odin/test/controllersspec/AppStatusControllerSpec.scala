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
class AppStatusControllerSpec extends Specification {
  "AppStatusControllerTest" should {
    "アプリケーションステータスをレンダリング" in new WithApplication{
      val appStatus = route(FakeRequest(GET, "/status")).get

      status(appStatus) must equalTo(OK)
      contentType(appStatus) must beSome.which(_ == "text/html")
      contentAsString(appStatus) must contain ("アプリケーションのステータス")
    }
  }
}
