package training

import java.io.InputStream

import scala.io.Source

case class Person(id: Int, name: String, phones: List[Phone] = List())

case class Phone(personId: Int, phoneType: String, number: String)

object Phone {
  def loadFromStream(stream: InputStream)(implicit phoneParser: PhoneParser): Iterator[Phone] =
    Source.fromInputStream(stream).getLines().map(phoneParser)

  def parse(raw: String)( implicit phoneParser: PhoneParser ) = phoneParser(raw)
}

trait PhoneParser extends (String => Phone)

object PhoneParser {
  implicit object DefaultPhoneParser extends PhoneParser {
    def apply(raw: String) = raw.split(",") match {
      case Array(id, phoneType, number) => Phone(id.toInt, phoneType, number.trim)
      case _ => throw new RuntimeException("ARRRGGH!")
    }
  }
}
