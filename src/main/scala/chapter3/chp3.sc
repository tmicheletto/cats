import chapter3._
import chapter3.Codec._
import chapter3.Printable._

final case class Box[A](value: A)

implicit def boxStringPrintable(implicit printable: Printable[String]): Printable[Box[String]] =
  printable.contramap[Box[String]](b => b.value)

implicit def boxBooleanPrintable(implicit printable: Printable[Boolean]): Printable[Box[Boolean]] =
  printable.contramap[Box[Boolean]](b => b.value)

format("Hello world")

format(Box(true))

implicit val stringCodec = new Codec[String] {
  def encode(value: String) = value

  def decode(value: String) = value
}

implicit val intCodec: Codec[Int] = stringCodec.imap[Int](_.toInt, _.toString)
implicit val doubleCodec: Codec[Double] = stringCodec.imap[Double](_.toDouble, _.toString)

implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] =
  c.imap[Box[A]](Box(_), _.value)

encode(123.4)

decode[Double]("123.4")

encode(Box(123.4))

decode[Box[Double]]("123.4")
