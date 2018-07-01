enablePlugins(JavaAppPackaging)

import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.naumanbadar",
      scalaVersion := "2.12.6",
      version := "0.1.0-SNAPSHOT"
    )),
  name := "Scala Project Installer",
  libraryDependencies += scalaTest % Test,
  libraryDependencies += ammoniteio
)

val installLocal = taskKey[Unit]("Links binary to your $HOME/.sclins")

installLocal := {
  import ammonite.ops._
  val log = streams.value.log
  //compile
  (Compile / compile).value
  //stage
  val stagePath: String = (Universal / stage).value.getAbsolutePath
//  log.info(s"stage file absolute path: $stagePath")

//  log.info((Universal / executableScriptName).value)
//  val universalDirectory = Path((Universal / target).value.getAbsolutePath)
//  log.info(s"Universal directory = ${universalDirectory.toString()}")

  val binaryName = (Universal / executableScriptName).value
  //  val completePath = universalDirectory / 'stage / 'bin / normalizedName.value
  val completePath = Path(stagePath) / 'bin / binaryName

  val link = home / ".sclins" / binaryName

  log.info(s"""Linking your script at "$completePath" to "$link"""")

//  ln(home/".sclins"/normalizedName.value,completePath)
//  ln(completePath,home/".sclins"/normalizedName.value)
  %('ln, "-sf", completePath, link)(pwd)

}
