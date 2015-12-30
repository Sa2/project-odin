import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
  * Created by Sa2 on 2015/12/31.
  */
@RunWith(classOf[JUnitRunner])
class ApiSpec extends Specification {
  "Api" should {

    "Api call test /user/api/v1/alone/1" in new WithApplication {
      val user = route(FakeRequest(GET, "/user/api/v1/alone/1")).get
      val regexStr = ".*user1.*".r

      status(user) must equalTo(OK)
      contentType(user) must beSome.which(_ == "application/json")
      user match {
        case regexStr(_*) => true
        case _ => false
      }
    }

    "Api call test /user/api/v1/list" in new WithApplication {
      val users = route(FakeRequest(GET, "/user/api/v1/list")).get
      val regexStr = ".*users.*".r

      status(users) must equalTo(OK)
      contentType(users) must beSome.which(_ == "application/json")
      users match {
        case regexStr(_*) => true
        case _ => false
      }
    }
  }
}
