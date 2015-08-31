import slick.codegen.SourceCodeGenerator
import slick.{ model => m }

lazy val databaseUrl = "jdbc:mysql://localhost/gungnir_db"
lazy val databaseUser = "travis"
lazy val databasePassword = ""

lazy val web = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(slickCodegenSettings:_*)
  .settings(
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      jdbc,
      "com.typesafe.slick" %% "slick" % "3.0.0",
      "com.typesafe.slick" %% "slick-codegen" % "3.0.0",
      "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
      "mysql" % "mysql-connector-java" % "5.1.35"
    ),
    slickCodegenDatabaseUrl := databaseUrl,
    slickCodegenDatabaseUser := databaseUser,
    slickCodegenDatabasePassword := databasePassword,
    slickCodegenDriver := slick.driver.MySQLDriver,
    slickCodegenJdbcDriver := "com.mysql.jdbc.Driver",
    slickCodegenOutputPackage := "models.dao",
    slickCodegenExcludedTables := Seq("schema_version"),
    slickCodegenCodeGenerator := { (model:  m.Model) =>
      new SourceCodeGenerator(model) {
        override def code =
          "import com.github.tototoshi.slick.MySQLJodaSupport._\n" + "import org.joda.time.DateTime\n" + super.code
        override def Table = new Table(_) {
          override def Column = new Column(_) {
            override def rawType = model.tpe match {
              case "java.sql.Timestamp" => "DateTime" // kill j.s.Timestamp
              case _ =>
                super.rawType
            }
          }
        }
      }
    },
    sourceGenerators in Compile <+= slickCodegen
  )
