package controllers

import jp.t2v.lab.play2.auth._

import scala.reflect.ClassTag

/**
  * Created by Sa2 on 2016/01/10.
  */
trait AuthConfigImpl extends AuthConfig {
  /**
    * A type that is used to identify a user.
    * `String`, `Int`, `Long` and so on.
    */
  type Id = String

  /**
    * A type that represents a user in your application.
    * `User`, `Account` and so on.
    */
//  type User = Account

  /**
    * A type that is defined by every action for authorization.
    * This sample uses the following trait:
    *
    * sealed trait Role
    * case object Administrator extends Role
    * case object NormalUser extends Role
    */
//  type Authority = Role

  /**
    * A `ClassTag` is used to retrieve an id from the Cache API.
    * Use something like this:
    */
//  val idTag: ClassTag[Id] = classTag[Id]

  /**
    * The session timeout in seconds
    */
  val sessionTimeoutInSeconds: Int = 3600

}
