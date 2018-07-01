package example

object Hello extends Greeting with App {
  println(greeting)

  println(s"greeting = ${args.toList}")
}

trait Greeting {
  lazy val greeting: String = "hello"
}
