import cats.Functor

package object chapter3 {

  implicit val treeFunctor: Functor[Tree] =
    new Functor[Tree] {
      def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
        case Branch(left, right) =>
          Branch(map(left)(f), map(right)(f))
        case Leaf(a) => Leaf[B](f(a))
      }
    }

  implicit val stringPrintable: Printable[String] =
    new Printable[String] {
      def format(value: String): String =
        "\"" + value + "\""
    }
  implicit val booleanPrintable: Printable[Boolean] =
    new Printable[Boolean] {
      def format(value: Boolean): String =
        if (value) "yes" else "no"
    }
}
