import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.libs.json.Json

import play.api.test._
import play.api.test.Helpers._


case class User(id: Int, userId: String, password: String, name: String, roleId: Int, isLock: Boolean)

object User {
  implicit def jsonReads = Json.reads[User]
}

/**
  * Created by Sa2 on 2015/12/31.
  */
@RunWith(classOf[JUnitRunner])
class ApiSpec extends Specification {

  "Api" should {

    val userName = "fakeuser"

    "Api call test /user/api/v1/fetch/id/1" in new WithApplication {
      val user = route(FakeRequest(GET, "/user/api/v1/fetch/id/1")).get
      val regexStr = ".*user1.*".r

      status(user) must equalTo(OK)
      contentType(user) must beSome.which(_ == "application/json")
      contentAsString(user) match {
        case regexStr(_*) => true
        case _ => None.get
      }
    }

    "Api call test /user/api/fetch/list" in new WithApplication {
      val users = route(FakeRequest(GET, "/user/api/v1/fetch/list")).get
      val regexStr = ".*users.*".r

      status(users) must equalTo(OK)
      contentType(users) must beSome.which(_ == "application/json")
      contentAsString(users) match {
        case regexStr(_*) => true
        case _ => None.get
      }
    }

    "Api call test /user/api/v1/create" in new WithApplication {
      val requestParam = Json.obj(
        "id"            -> 5,
        "userId"        -> userName,
        "password"      -> "password",
        "name"          -> "テストユーザー",
        "roleId"        -> 1,
        "isLock"        -> false
      )
      val regexStr = ".*success.*".r

      val request = FakeRequest(POST, "/user/api/v1/create").withJsonBody(requestParam)
      val response = route(request).get
      contentAsString(response) match {
        case regexStr(_*) => true
        case _ => None.get
      }
    }

    "Api call test /user/api/v1/fetch/userid/" + userName in new WithApplication {
      val user = route(FakeRequest(GET, "/user/api/v1/fetch/userid/" + userName)).get
      val regexTarget = ".*" + userName + ".*"
      val regexStr = regexTarget.r

      status(user) must equalTo(OK)
      contentType(user) must beSome.which(_ == "application/json")
      contentAsString(user) match {
        case regexStr(_*) => true
        case _ => None.get
      }
    }

    "Api call test /user/api/v1/update" in new WithApplication {
      // idを取得
      val userJson = route(FakeRequest(GET, "/user/api/v1/fetch/userid/" + userName)).get
      status(userJson) must equalTo(OK)
      val user: User = Json.parse(contentAsString(userJson)).validate[User].get

      val updatedName = "更新したテストユーザー"
      val requestParam = Json.obj(
        "id"            -> user.id,
        "userId"        -> user.userId,
        "password"      -> user.password,
        "name"          -> updatedName,
        "roleId"        -> user.roleId,
        "isLock"        -> user.isLock
      )
      val regexStr = ".*success.*".r
      val request = FakeRequest(POST, "/user/api/v1/update").withJsonBody(requestParam)
      val response = route(request).get
      contentAsString(response) match {
        case regexStr(_*) => true
        case _ => None.get
      }

      Thread.sleep(100)

      // update後のデータを確認する
      val lockedUserJson = route(FakeRequest(GET, "/user/api/v1/fetch/userid/" + userName)).get
      status(userJson) must equalTo(OK)
      val lockedUser: User = Json.parse(contentAsString(lockedUserJson)).validate[User].get
      val regexName = updatedName.r
      lockedUser.name match {
        case regexName(_*) => true
        case _ => None.get
      }
    }

    "Api call test /user/api/v1/lock/" + userName in new WithApplication {
      // idを取得
      val userJson = route(FakeRequest(GET, "/user/api/v1/fetch/userid/" + userName)).get
      status(userJson) must equalTo(OK)
      val user: User = Json.parse(contentAsString(userJson)).validate[User].get

      val regexStr = ".*success.*".r
      val requestUri = "/user/api/v1/lock/" + user.id
      val request = FakeRequest(POST, requestUri)
      val response = route(request).get
      contentAsString(response) match {
        case regexStr(_*) => true
        case _ => None.get
      }

      Thread.sleep(100)

      // lock後のデータを確認する
      val lockedUserJson = route(FakeRequest(GET, "/user/api/v1/fetch/userid/" + userName)).get
      status(userJson) must equalTo(OK)
      val lockedUser: User = Json.parse(contentAsString(lockedUserJson)).validate[User].get
      lockedUser.isLock match {
        case true => true
        case _ => None.get
      }
    }

    "Api call test /user/api/v1/remove/" + userName in new WithApplication {
      // idを取得
      val userJson = route(FakeRequest(GET, "/user/api/v1/fetch/userid/" + userName)).get
      status(userJson) must equalTo(OK)
      val user: User = Json.parse(contentAsString(userJson)).validate[User].get

      // 削除したかを確認する
      val regexStr = ".*success.*".r
      val requestUri = "/user/api/v1/remove/" + user.id
      val request = FakeRequest(POST, requestUri)
      val response = route(request).get
      contentAsString(response) match {
        case regexStr(_*) => true
        case _ => None.get
      }
    }
  }
}
