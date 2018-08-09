import chapter1._
//import cats._
import cats.implicits._

val c = Cat("Sophie", 17, "Grey")
c.show

val cat1 = Cat("Garfield", 38, "orange and black")
val cat2 = Cat("Heathcliff", 33, "orange and black")
val optionCat1 = Option(cat1)
val optionCat2 = Option.empty[Cat]

optionCat1 === optionCat2