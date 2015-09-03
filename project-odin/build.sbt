name := """project-odin"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
//  jdbc,
//  cache,
//  ws,
  specs2 % Test,
  "com.typesafe.play" %% "play-slick" % "1.0.0",
//  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.0.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
  "mysql" % "mysql-connector-java" % "5.1.35"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


// Slick code generator
// command "sbt gen-tables"
slick <<= slickCodeGenTask // register sbt command
// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = "app"

  val url = "jdbc:mysql://localhost/gungnir_db"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val username = "travis"
  val password = ""
  val pkg = "models"

  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files,
    Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  val fname = outputDir + "/models/dao/Tables.scala"
  Seq(file(fname))
}