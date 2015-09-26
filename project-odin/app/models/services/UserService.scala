package models.services

import java.util.UUID
import javax.inject.Inject

import models.repositories.UserRepository
import org.apache.commons.codec.digest.DigestUtils
import viewmodels.UserViewModel

import scala.concurrent.Future


/**
 * Created by Sa2 on 15/09/21.
 */
class UserService @Inject()(val userRepository: UserRepository) {
  import UserService._

  def findByUserId(userId: String): Future[Option[UserViewModel]] = {
    userRepository.findByUserId(userId).map(
      user => user match {
        case Some(user) => Some(new UserViewModel(user))
        case _ => None
      }
    )
  }

  def authenticate(){}
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