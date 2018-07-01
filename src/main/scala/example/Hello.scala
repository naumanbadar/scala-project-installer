package example

import ammonite.ops._

object Hello extends Greeting with App {
  println(s"$greeting from $pwd")

  println(s"Commandline args = ${args.toList}")
}

trait Greeting {
  lazy val greeting: String = "hello"
}
