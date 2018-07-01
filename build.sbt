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
  val com = (Compile / compile).value
  val st  = (Universal / stage).value

  import ammonite.ops._
  println("current dir")
  println(s"pwd = ${pwd}")
  println((Universal / executableScriptName).value)
  val absolutePath = Path((Universal / target).value.getAbsolutePath)
  println(absolutePath)

  val completePath = absolutePath / 'stage / 'bin / normalizedName.value

  println(s"completePath = ${completePath}")

//  ln(home/".sclins"/normalizedName.value,completePath)
//  ln(completePath,home/".sclins"/normalizedName.value)
  %('ln, "-sfv", completePath, home / ".sclins" / normalizedName.value)(pwd)

}
