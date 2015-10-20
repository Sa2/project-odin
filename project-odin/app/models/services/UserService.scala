package models.services

import java.util.UUID
import javax.inject.Inject

import com.google.inject.ImplementedBy
import forms.LoginForm
import models.repositories.UserRepositoryLike
import org.apache.commons.codec.digest.DigestUtils
import viewmodels.UserViewModel

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Logger

/**
 * Created by Sa2 on 15/09/21.
 */
@ImplementedBy(classOf[UserService])
trait UserServiceLike {
  def findByUserId(userId: String): Future[Option[UserViewModel]]

  def authenticate(form: LoginForm): Future[Option[UserViewModel]]
}

class UserService @Inject()(val userRepository: UserRepositoryLike) extends UserServiceLike {
  import UserService._

  def findByUserId(userId: String): Future[Option[UserViewModel]] = {
    Logger.info(userId)
    userRepository.findByUserId(userId).map( userOpt =>
      userOpt.flatMap { user =>
        Logger.info(user.userId)
        Some(new UserViewModel(user))
      }
    )
  }

  def authenticate(form: LoginForm): Future[Option[UserViewModel]] = {
    Logger.info("pass1")
    userRepository.findByUserId(form.userId).map { userOpt =>
      Logger.info("pass2")
      userOpt.flatMap { user =>
        Logger.info("pass3")
        if (hashAndStretch(form.password, user.passwordSalt, STRETCH_LOOP_COUNT) == user.password)
          Some(new UserViewModel(user))
        else
          None
      }
    }
  }
}

object UserService {
  val STRETCH_LOOP_COUNT = 1000

  def hashAndStretch(plain: String, salt: String, loopCnt: Int): String = {
    var hashed: String = ""
    (1 to STRETCH_LOOP_COUNT).foreach(i =>
      hashed = DigestUtils.sha256Hex(hashed + plain + salt)
    )

    Logger.info("通過")
    Logger.info(hashed)
    hashed
  }

  def createPasswordSalt(): String = {
    DigestUtils.sha256Hex(UUID.randomUUID().toString())
  }
}