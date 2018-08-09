package chapter1

import cats._
import cats.implicits._

final case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val catShow: Show[Cat] =
    new Show[Cat] {
      def show(cat: Cat): String =
        s"${cat.name} is a ${cat.age} year-old ${cat.color} cat."
    }

  implicit val catEq: Eq[Cat] =
    Eq.instance[Cat] { (cat1, cat2) =>
      cat1.name === cat2.name && cat1.color === cat2.color && cat1.age === cat2.age
    }
}

sealed trait Printable[A] {
  def format(input: A): String
}

object PrintableInstances {
  implicit val printableString: Printable[String] = new Printable[String] {
    def format(input: String): String = input
  }

  implicit val printableInt: Printable[Int] = new Printable[Int] {
    def format(input: Int): String = input.toString
  }

  implicit val printableCat: Printable[Cat] = new Printable[Cat] {
    def format(input: Cat): String = s"${input.name} is a ${input.age} year-old ${input.color} cat."
  }
}

object Printable {
  def format[A](input: A)(implicit p: Printable[A]): String =
    p.format(input)

  def print[A](input: A)(implicit p: Printable[A]): Unit =
    println(format(input))
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      p.format(value)

    def print(implicit p: Printable[A]): Unit =
      println(p.format(value))
  }
}
