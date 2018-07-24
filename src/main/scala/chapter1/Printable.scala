package chapter1

sealed trait Printable[A] {
  def format(a: A): String
}

object PrintableInstances {
  implicit val printableString: Printable[String] = new Printable[String] {
    def format(a: String): String = a
  }

  implicit val printableInt: Printable[Int] = new Printable[Int] {
    def format(a: Int): String = a.toString
  }
}

object Printable {
  def format[A](a: A)(implicit p: Printable[A]): String =
    p.format(a)

  def print[A](a: A)(implicit p: Printable[A]): Unit =
    Console.println(format(a, p))
}
