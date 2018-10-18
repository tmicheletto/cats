import ammonite.ops._
import $ivy.`org.typelevel::cats-core:1.3.0`
import cats.data.State
import cats.syntax.applicative._

def evalInput(input: String): Int = {
  evalAll(input.split(' ').toList).runA(Nil).value
}

def evalAll(input: List[String]): CalcState[Int] = {
  input.foldLeft(0.pure[CalcState]) { (a, b) =>
    a.flatMap(_ => evalOne(b))
  }
}

type CalcState[A] = State[List[Int], A]
def evalOne(sym: String): CalcState[Int] =
  sym match {
    case "+" => operator((a, b) => a + b)
    case "-" => operator((a, b) => a - b)
    case "*" => operator((a, b) => a * b)
    case "/" => operator((a, b) => a / b)
    case num => operand(num.toInt)
  }

def operand(num: Int): CalcState[Int] =
  State[List[Int], Int] { stack => (num :: stack, num) }

def operator(func: (Int, Int) => Int): CalcState[Int] =
  State[List[Int], Int] {
    case a :: b :: tail =>
      val ans = func(a, b)
      (ans :: tail, ans)

    case _ => sys.error("Fail!")
  }

println(evalInput("1 2 + 3 4 + *"))