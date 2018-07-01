//enablePlugins(JavaAppPackaging)
//enablePlugins(UniversalPlugin)
enablePlugins(JavaAppPackaging)

import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.example",
      scalaVersion := "2.12.6",
      version := "0.1.0-SNAPSHOT"
    )),
  name := "TryPackage",
  libraryDependencies += scalaTest % Test,
  libraryDependencies += ammoniteio
)

val addToPath = taskKey[Unit]("link it")

addToPath := {
  import ammonite.ops._
  val log = streams.value.log
  val com = (Compile / compile).value
  val st  = (Universal / stage).value

  log.info("current dir")
  log.info(s"pwd = ${pwd}")
  log.info((Universal / executableScriptName).value)
  val absolutePath = Path((Universal / target).value.getAbsolutePath)
  log.info(absolutePath.toString())

  val completePath = absolutePath / 'stage / 'bin / normalizedName.value

  log.info(s"completePath = ${completePath}")

//  ln(home/".sclins"/normalizedName.value,completePath)
//  ln(completePath,home/".sclins"/normalizedName.value)
  %('ln, "-sfv", completePath, home / ".sclins" / normalizedName.value)(pwd)

}
