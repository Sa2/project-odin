package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = Article.ddl ++ File.ddl ++ User.ddl
  
  /** Entity class storing rows of table Article
   *  @param id Database column id DBType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param title Database column title DBType(VARCHAR), Length(64,true)
   *  @param body Database column body DBType(LONGTEXT), Length(2147483647,true), Default(None)
   *  @param isHide Database column is_hide DBType(BIT)
   *  @param postedUserId Database column posted_user_id DBType(INT UNSIGNED), Default(None)
   *  @param postDate Database column post_date DBType(DATETIME)
   *  @param updateDate Database column update_date DBType(TIMESTAMP) */
  case class ArticleRow(id: Int, title: String, body: Option[String] = None, isHide: Boolean, postedUserId: Option[Int] = None, postDate: java.sql.Timestamp, updateDate: java.sql.Timestamp)
  /** GetResult implicit for fetching ArticleRow objects using plain SQL queries */
  implicit def GetResultArticleRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Boolean], e4: GR[Option[Int]], e5: GR[java.sql.Timestamp]): GR[ArticleRow] = GR{
    prs => import prs._
    ArticleRow.tupled((<<[Int], <<[String], <<?[String], <<[Boolean], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table article. Objects of this class serve as prototypes for rows in queries. */
  class Article(_tableTag: Tag) extends Table[ArticleRow](_tableTag, "article") {
    def * = (id, title, body, isHide, postedUserId, postDate, updateDate) <> (ArticleRow.tupled, ArticleRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, title.?, body, isHide.?, postedUserId, postDate.?, updateDate.?).shaped.<>({r=>import r._; _1.map(_=> ArticleRow.tupled((_1.get, _2.get, _3, _4.get, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title DBType(VARCHAR), Length(64,true) */
    val title: Column[String] = column[String]("title", O.Length(64,varying=true))
    /** Database column body DBType(LONGTEXT), Length(2147483647,true), Default(None) */
    val body: Column[Option[String]] = column[Option[String]]("body", O.Length(2147483647,varying=true), O.Default(None))
    /** Database column is_hide DBType(BIT) */
    val isHide: Column[Boolean] = column[Boolean]("is_hide")
    /** Database column posted_user_id DBType(INT UNSIGNED), Default(None) */
    val postedUserId: Column[Option[Int]] = column[Option[Int]]("posted_user_id", O.Default(None))
    /** Database column post_date DBType(DATETIME) */
    val postDate: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("post_date")
    /** Database column update_date DBType(TIMESTAMP) */
    val updateDate: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("update_date")
    
    /** Foreign key referencing User (database name article_ibfk_1) */
    lazy val userFk = foreignKey("article_ibfk_1", postedUserId, User)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Article */
  lazy val Article = new TableQuery(tag => new Article(tag))
  
  /** Entity class storing rows of table File
   *  @param id Database column id DBType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param name Database column name DBType(VARCHAR), Length(256,true)
   *  @param fileBlob Database column file_blob DBType(LONGBLOB)
   *  @param articleId Database column article_id DBType(INT UNSIGNED)
   *  @param uploadDate Database column upload_date DBType(TIMESTAMP) */
  case class FileRow(id: Int, name: String, fileBlob: java.sql.Blob, articleId: Int, uploadDate: java.sql.Timestamp)
  /** GetResult implicit for fetching FileRow objects using plain SQL queries */
  implicit def GetResultFileRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Blob], e3: GR[java.sql.Timestamp]): GR[FileRow] = GR{
    prs => import prs._
    FileRow.tupled((<<[Int], <<[String], <<[java.sql.Blob], <<[Int], <<[java.sql.Timestamp]))
  }
  /** Table description of table file. Objects of this class serve as prototypes for rows in queries. */
  class File(_tableTag: Tag) extends Table[FileRow](_tableTag, "file") {
    def * = (id, name, fileBlob, articleId, uploadDate) <> (FileRow.tupled, FileRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, name.?, fileBlob.?, articleId.?, uploadDate.?).shaped.<>({r=>import r._; _1.map(_=> FileRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name DBType(VARCHAR), Length(256,true) */
    val name: Column[String] = column[String]("name", O.Length(256,varying=true))
    /** Database column file_blob DBType(LONGBLOB) */
    val fileBlob: Column[java.sql.Blob] = column[java.sql.Blob]("file_blob")
    /** Database column article_id DBType(INT UNSIGNED) */
    val articleId: Column[Int] = column[Int]("article_id")
    /** Database column upload_date DBType(TIMESTAMP) */
    val uploadDate: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("upload_date")
    
    /** Foreign key referencing Article (database name file_ibfk_1) */
    lazy val articleFk = foreignKey("file_ibfk_1", articleId, Article)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table File */
  lazy val File = new TableQuery(tag => new File(tag))
  
  /** Entity class storing rows of table User
   *  @param id Database column id DBType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param userId Database column user_id DBType(VARCHAR), Length(32,true)
   *  @param password Database column password DBType(VARCHAR), Length(256,true)
   *  @param name Database column name DBType(VARCHAR), Length(32,true)
   *  @param isLock Database column is_lock DBType(BIT)
   *  @param registerDate Database column register_date DBType(DATETIME)
   *  @param updateDate Database column update_date DBType(TIMESTAMP) */
  case class UserRow(id: Int, userId: String, password: String, name: String, isLock: Boolean, registerDate: java.sql.Timestamp, updateDate: java.sql.Timestamp)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean], e3: GR[java.sql.Timestamp]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[Boolean], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, "user") {
    def * = (id, userId, password, name, isLock, registerDate, updateDate) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, userId.?, password.?, name.?, isLock.?, registerDate.?, updateDate.?).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id DBType(VARCHAR), Length(32,true) */
    val userId: Column[String] = column[String]("user_id", O.Length(32,varying=true))
    /** Database column password DBType(VARCHAR), Length(256,true) */
    val password: Column[String] = column[String]("password", O.Length(256,varying=true))
    /** Database column name DBType(VARCHAR), Length(32,true) */
    val name: Column[String] = column[String]("name", O.Length(32,varying=true))
    /** Database column is_lock DBType(BIT) */
    val isLock: Column[Boolean] = column[Boolean]("is_lock")
    /** Database column register_date DBType(DATETIME) */
    val registerDate: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("register_date")
    /** Database column update_date DBType(TIMESTAMP) */
    val updateDate: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("update_date")
    
    /** Uniqueness Index over (userId) (database name user_id) */
    val index1 = index("user_id", userId, unique=true)
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}