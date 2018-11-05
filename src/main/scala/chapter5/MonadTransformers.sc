import ammonite.ops._
import $ivy.`org.typelevel::cats-core:1.3.0`


object MonadTransformerEx {

  import cats.data.EitherT
  import cats.instances.future._ // for Monad
  import cats.syntax.flatMap._ // for flatMap

  import scala.concurrent.{Await, Future}
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )

  type Response[A] = EitherT[Future, String, A]

  def getPowerLevel(autobot: String): Response[Int] = {
    powerLevels.get(autobot) match {
      case Some(l) => EitherT.right(Future(l))
      case None => EitherT.left(Future(s"Could not find power level for $autobot"))
    }
  }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = {
    for {
      pa1 <- getPowerLevel(ally1)
      pa2 <- getPowerLevel(ally2)
    } yield pa1 + pa2 > 15
  }

  def tacticalReport(ally1: String, ally2: String): String = {
    val stack = canSpecialMove(ally1, ally2).value

    Await.result(stack, 1.second) match {
      case Left(msg) => s"Comms error: $msg"
      case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
      case Right(false) => s"$ally1 and $ally2 need a recharge."
    }
  }

  def run() : String = {
    tacticalReport("Jazz", "Hot Rod")
  }
}

println(MonadTransformerEx.run())