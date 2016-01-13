name := """odin-auth-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
//  jdbc,
  cache,
  ws,
  specs2 % Test,
  "jp.t2v" %% "play2-auth"        % "0.14.1",
//  "jp.t2v" %% "play2-auth-social" % "0.14.1", // for social login
  "jp.t2v" %% "play2-auth-test"   % "0.14.1" % "test"
//  play.sbt.Play.autoImport.cache // only when you use default IdContainer
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
