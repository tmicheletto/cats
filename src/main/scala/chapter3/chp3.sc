import chapter3._
//import chapter3.Printable._

//import cats.syntax.functor._
//
//Tree.branch(Leaf(1), Leaf(2)).map(_ * 2)
//Tree.leaf(100).map(_ * 2)
//
//format("hello")
//
//format(true)

final case class Box[A](value: A)

implicit def boxStringPrintable(implicit printable: Printable[String]): Printable[Box[String]] =
  printable.contramap[Box[String]](b => b.value)

implicit def boxBooleanPrintable(implicit printable: Printable[Boolean]): Printable[Box[Boolean]] =
  printable.contramap[Box[Boolean]](b => b.value)

chapter3.Printable.format("Hello world")

//format(Box(true))
