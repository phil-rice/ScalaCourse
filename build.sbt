name := "scalaCourse"

version := "1.0"

scalaVersion := "2.12.2"

val versions = new {
  val scalaTest = "3.0.3"
  val scalaMock = "3.5.0"
}
libraryDependencies += "org.scalatest" %% "scalatest" % versions.scalaTest % "test"

libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % versions.scalaMock % "test"

