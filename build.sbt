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

  val binaryName = (Universal / executableScriptName).value
  val binaryPath = Path(stagePath) / 'bin / binaryName
  val binaryLink = home / ".sclins" / binaryName

  log.info(s"""Linking your script at "$binaryPath" to "$binaryLink"""")

  %('ln, "-sf", binaryPath, binaryLink)(pwd)

}
