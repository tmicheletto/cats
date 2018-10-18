import ammonite.ops._
import $ivy.`org.typelevel::cats-core:1.3.0`
import cats.Id

import scala.language.higherKinds
    trait Monad[F[_]] {
      def pure[A](a: A): F[A]

      def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

      def map[A, B](value: F[A])(func: A => B): F[B] =
        flatMap(value)(a => pure[B](func(a)))
    }

    def pure[A](value: A): Id[A] =
      value

    def map[A, B](initial: Id[A])(func: A => B): Id[B] =
      func(initial)

    def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] =
      func(initial)