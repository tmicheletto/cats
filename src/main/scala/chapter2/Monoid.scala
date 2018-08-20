//package chapter2
//
//trait Semigroup[A] {
//  def combine(x: A, y: A): A
//}
//
//trait Monoid[A] extends Semigroup[A] {
//  def empty: A
//}
//object Monoid {
//  def apply[A](implicit monoid: Monoid[A]) =
//    monoid
//}
//
//object MonoidInstances {
//  implicit val booleanAndMonoid = new Monoid[Boolean] {
//    def empty: Boolean = true
//    def combine(x: Boolean, y: Boolean): Boolean = x && y
//  }
//
//  implicit val booleanOrMonoid = new Monoid[Boolean] {
//    def empty: Boolean = false
//    def combine(x: Boolean, y: Boolean): Boolean = x || y
//  }
//
//  implicit val booleanXOrMonoid = new Monoid[Boolean] {
//    def empty: Boolean = false
//    def combine(x: Boolean, y: Boolean): Boolean = (x && !y) || (!x || y)
//  }
//
//  implicit val booleanXNorMonoid = new Monoid[Boolean] {
//    def empty: Boolean = true
//    def combine(x: Boolean, y: Boolean): Boolean = (!x || y) && (x || !y)
//  }
//
//  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
//    def combine(a: Int, b: Int) = a + b
//    def empty = 0
//  }
//
//  implicit def setIntersectionSemigroup[A]: Semigroup[Set[A]] =
//    new Semigroup[Set[A]] {
//      def combine(a: Set[A], b: Set[A]) =
//        a intersect b
//    }
//
//  implicit def symDiffMonoid[A]: Monoid[Set[A]] =
//    new Monoid[Set[A]] {
//      def combine(a: Set[A], b: Set[A]): Set[A] =
//        (a diff b) union (b diff a)
//      def empty: Set[A] = Set.empty
//    }
//}