import sbt.Keys.scalaVersion

name := "scalaCourse"


val versions = new {
  val scalaVersion = "2.12.2"
  val scalaCourseVersion = "1.0"
  val scalaTest = "3.0.3"
  val scalaMock = "3.5.0"
}

val commonSettings = Seq(
  version := versions.scalaCourseVersion,
  scalaVersion := versions.scalaVersion,
  libraryDependencies += "org.scalatest" %% "scalatest" % versions.scalaTest % "test",
  libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % versions.scalaMock % "test"

)
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = true)
val jsonSettings = commonSettings ++ Seq(
  name := "json"
)

val domainSettings = commonSettings ++ Seq(
  name := "domain"
)
val mainSettings = commonSettings ++ Seq(
  name := "main",
  mainClass in assembly := Some("main.Main")
)

lazy val utilities = (project in file("modules/utilities")).settings(commonSettings: _*)

lazy val json = (project in file("modules/json")).settings(jsonSettings: _*).dependsOn(utilities % "test->test;compile->compile").aggregate(utilities)

lazy val domain = (project in file("modules/domain")).settings(domainSettings: _*).dependsOn(utilities % "test->test;compile->compile").aggregate(utilities)

lazy val main = (project in file("modules/main")).settings(mainSettings: _*).dependsOn(json, domain % "test->test;compile->compile", utilities % "test->test;compile->compile").aggregate(json, domain)

lazy val root = (project in file (".")).
  settings(commonSettings: _*).
  settings(mainClass in assembly := Some("main.Main")).dependsOn(main).aggregate(main)