package utils.validation

import play.api.data.validation.{Valid, ValidationError, Invalid, Constraint}

/**
 * Created by Sa2 on 15/10/19.
 */
object CustomConstraints extends CustomConstraints

trait CustomConstraints {

  private val userIdRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r
  def userId: Constraint[String] = Constraint[String]("constraint.custom.userid") { e =>
    if ((e == null) || (e.trim.isEmpty)) Valid // use nonEmpty or custom nonEmpty constraints
    else userIdRegex.findFirstMatchIn(e)
      .map(_ => Valid)
      .getOrElse(Invalid(ValidationError("validation.error.invalid.userid")))
  }
}