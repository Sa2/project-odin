package models.dao
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import com.github.tototoshi.slick.MySQLJodaSupport._
  import org.joda.time.DateTime
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Articles.schema ++ Files.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Articles
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(VARCHAR), Length(64,true)
   *  @param body Database column body SqlType(LONGTEXT), Length(2147483647,true), Default(None)
   *  @param viewCount Database column view_count SqlType(BIGINT UNSIGNED), Default(0)
   *  @param isHide Database column is_hide SqlType(BIT)
   *  @param postedUserId Database column posted_user_id SqlType(INT UNSIGNED), Default(None)
   *  @param postDate Database column post_date SqlType(DATETIME)
   *  @param updateDate Database column update_date SqlType(TIMESTAMP) */
  case class ArticlesRow(id: Int, title: String, body: Option[String] = None, viewCount: Long = 0L, isHide: Boolean, postedUserId: Option[Int] = None, postDate: DateTime, updateDate: DateTime)
  /** GetResult implicit for fetching ArticlesRow objects using plain SQL queries */
  implicit def GetResultArticlesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Long], e4: GR[Boolean], e5: GR[Option[Int]], e6: GR[DateTime]): GR[ArticlesRow] = GR{
    prs => import prs._
    ArticlesRow.tupled((<<[Int], <<[String], <<?[String], <<[Long], <<[Boolean], <<?[Int], <<[DateTime], <<[DateTime]))
  }
  /** Table description of table articles. Objects of this class serve as prototypes for rows in queries. */
  class Articles(_tableTag: Tag) extends Table[ArticlesRow](_tableTag, "articles") {
    def * = (id, title, body, viewCount, isHide, postedUserId, postDate, updateDate) <> (ArticlesRow.tupled, ArticlesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), body, Rep.Some(viewCount), Rep.Some(isHide), postedUserId, Rep.Some(postDate), Rep.Some(updateDate)).shaped.<>({r=>import r._; _1.map(_=> ArticlesRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(VARCHAR), Length(64,true) */
    val title: Rep[String] = column[String]("title", O.Length(64,varying=true))
    /** Database column body SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val body: Rep[Option[String]] = column[Option[String]]("body", O.Length(2147483647,varying=true), O.Default(None))
    /** Database column view_count SqlType(BIGINT UNSIGNED), Default(0) */
    val viewCount: Rep[Long] = column[Long]("view_count", O.Default(0L))
    /** Database column is_hide SqlType(BIT) */
    val isHide: Rep[Boolean] = column[Boolean]("is_hide")
    /** Database column posted_user_id SqlType(INT UNSIGNED), Default(None) */
    val postedUserId: Rep[Option[Int]] = column[Option[Int]]("posted_user_id", O.Default(None))
    /** Database column post_date SqlType(DATETIME) */
    val postDate: Rep[DateTime] = column[DateTime]("post_date")
    /** Database column update_date SqlType(TIMESTAMP) */
    val updateDate: Rep[DateTime] = column[DateTime]("update_date")

    /** Foreign key referencing Users (database name articles_ibfk_1) */
    lazy val usersFk = foreignKey("articles_ibfk_1", postedUserId, Users)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Articles */
  lazy val Articles = new TableQuery(tag => new Articles(tag))

  /** Entity class storing rows of table Files
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(256,true)
   *  @param fileBlob Database column file_blob SqlType(LONGBLOB)
   *  @param articleId Database column article_id SqlType(INT UNSIGNED)
   *  @param uploadDate Database column upload_date SqlType(TIMESTAMP) */
  case class FilesRow(id: Int, name: String, fileBlob: java.sql.Blob, articleId: Int, uploadDate: DateTime)
  /** GetResult implicit for fetching FilesRow objects using plain SQL queries */
  implicit def GetResultFilesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Blob], e3: GR[DateTime]): GR[FilesRow] = GR{
    prs => import prs._
    FilesRow.tupled((<<[Int], <<[String], <<[java.sql.Blob], <<[Int], <<[DateTime]))
  }
  /** Table description of table files. Objects of this class serve as prototypes for rows in queries. */
  class Files(_tableTag: Tag) extends Table[FilesRow](_tableTag, "files") {
    def * = (id, name, fileBlob, articleId, uploadDate) <> (FilesRow.tupled, FilesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(fileBlob), Rep.Some(articleId), Rep.Some(uploadDate)).shaped.<>({r=>import r._; _1.map(_=> FilesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(256,true) */
    val name: Rep[String] = column[String]("name", O.Length(256,varying=true))
    /** Database column file_blob SqlType(LONGBLOB) */
    val fileBlob: Rep[java.sql.Blob] = column[java.sql.Blob]("file_blob")
    /** Database column article_id SqlType(INT UNSIGNED) */
    val articleId: Rep[Int] = column[Int]("article_id")
    /** Database column upload_date SqlType(TIMESTAMP) */
    val uploadDate: Rep[DateTime] = column[DateTime]("upload_date")

    /** Foreign key referencing Articles (database name files_ibfk_1) */
    lazy val articlesFk = foreignKey("files_ibfk_1", articleId, Articles)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Files */
  lazy val Files = new TableQuery(tag => new Files(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(VARCHAR), Length(32,true)
   *  @param password Database column password SqlType(VARCHAR), Length(256,true)
   *  @param name Database column name SqlType(VARCHAR), Length(32,true)
   *  @param isLock Database column is_lock SqlType(BIT)
   *  @param registerDate Database column register_date SqlType(DATETIME)
   *  @param updateDate Database column update_date SqlType(TIMESTAMP) */
  case class UsersRow(id: Int, userId: String, password: String, name: String, isLock: Boolean, registerDate: DateTime, updateDate: DateTime)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[DateTime]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[Boolean], <<[DateTime], <<[DateTime]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (id, userId, password, name, isLock, registerDate, updateDate) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userId), Rep.Some(password), Rep.Some(name), Rep.Some(isLock), Rep.Some(registerDate), Rep.Some(updateDate)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(VARCHAR), Length(32,true) */
    val userId: Rep[String] = column[String]("user_id", O.Length(32,varying=true))
    /** Database column password SqlType(VARCHAR), Length(256,true) */
    val password: Rep[String] = column[String]("password", O.Length(256,varying=true))
    /** Database column name SqlType(VARCHAR), Length(32,true) */
    val name: Rep[String] = column[String]("name", O.Length(32,varying=true))
    /** Database column is_lock SqlType(BIT) */
    val isLock: Rep[Boolean] = column[Boolean]("is_lock")
    /** Database column register_date SqlType(DATETIME) */
    val registerDate: Rep[DateTime] = column[DateTime]("register_date")
    /** Database column update_date SqlType(TIMESTAMP) */
    val updateDate: Rep[DateTime] = column[DateTime]("update_date")

    /** Uniqueness Index over (userId) (database name user_id) */
    val index1 = index("user_id", userId, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
