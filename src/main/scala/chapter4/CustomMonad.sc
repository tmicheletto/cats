import ammonite.ops._
import $ivy.`org.typelevel::cats-core:1.3.0`
import cats.Monad
import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap

  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  implicit val treeMonad = new Monad[Tree] {
    def flatMap[A, B](tree: Tree[A])(f: A => Tree[B]): Tree[B] =
      tree match {
        case Branch(l, r) => Branch(flatMap(l)(f), flatMap(r)(f))
        case Leaf(v) => f(v)
      }

    def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] =
      f(a) match {
        case Leaf(Left(v)) => tailRecM(v)(f)
        case Leaf(Right(v)) => Leaf(v)
        case Branch(left, right) =>
          Branch(
            flatMap(left) {
              case Left(l) => tailRecM(l)(f)
              case Right(r) => pure(r)
            },
            flatMap(right) {
              case Left(l) => tailRecM(l)(f)
              case Right(r) => pure(r)
            }
          )
      }

    def pure[A](x: A): Tree[A] =
      leaf(x)
  }

branch(leaf(100), leaf(200)).flatMap(x => branch(leaf(x - 1), leaf(x + 1)))

val tree = for {
  a <- branch(leaf(100), leaf(200))
  b <- branch(leaf(a - 10), leaf(a + 10))
  c <- branch(leaf(b - 1), leaf(b + 1))
} yield c

println(tree)

val tree2 = branch(leaf(100), leaf(200))
  .flatMap(a => branch(leaf(a - 10), leaf(a + 10))
    .flatMap(b => branch(leaf(b - 1), leaf(b + 1))
      .map(c => c)
    ))

println(tree2)