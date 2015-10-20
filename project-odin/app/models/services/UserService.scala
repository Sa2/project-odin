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
    userRepository.findByUserId(userId).map( userOpt =>
      userOpt.flatMap { user =>
        Some(new UserViewModel(user))
      }
    )
  }

  def authenticate(form: LoginForm): Future[Option[UserViewModel]] = {
    userRepository.findByUserId(form.userId).map { userOpt =>
      userOpt.flatMap { user =>
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
    hashed
  }

  def createPasswordSalt(): String = {
    DigestUtils.sha256Hex(UUID.randomUUID().toString())
  }
}