import chapter2._
import chapter2.SuperAdderInstances._

import cats.instances.int._

val l1 = List(1, 2, 3)

SuperAdder.add(l1)

import cats.instances.option._
val l2 = List(Some(4), None, Some(5), Some(6))

SuperAdder.add(l2)

val o1 = Order(99.99, 3.5)
val o2 = Order(66.66, 1.75)

val o3 = SuperAdder.add(List(o1, o2))