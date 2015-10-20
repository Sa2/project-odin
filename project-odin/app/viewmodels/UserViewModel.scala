package viewmodels

import models.dao.Tables.UsersRow
import org.joda.time.DateTime
import sun.security.util.Password

/**
 * Created by Sa2 on 15/09/21.
 */
case class UserViewModel (id: Int,
                           userId: String,
                           password: String,
                           passwordSalt: String,
                           name: String,
                           roleId: Long,
                           isLock: Boolean,
                           registerDate: DateTime,
                           updateDate: DateTime) {
  def this(row: UsersRow) = this(
    row.id,
    row.userId,
    row.password,
    row.passwordSalt,
    row.name,
    row.roleId,
    row.isLock,
    row.registerDate,
    row.updateDate
  )
}
