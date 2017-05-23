package training

import java.io.InputStream

import scala.io.Source

case class Person(id: Int, name: String, phones: List[Phone] = List())

object Person {
  def parse(raw: String, phoneMap: Map[Int, List[Phone]])(implicit personParser: PersonParser) = personParser(raw, phoneMap)
}

case class Phone(personId: Int, phoneType: String, number: String)

trait PersonParser extends ((String, Map[Int, List[Phone]]) => Person)

object PersonParser {

  implicit object DefaultPersonParser extends PersonParser {
    override def apply(raw: String, phoneMap: Map[Int, List[Phone]]): Person = raw.split(",") match {
      case Array(id, name) => Person(id.toInt, name, phoneMap.getOrElse(id.toInt, List()))
      case _ => throw new RuntimeException("ARRRGGH!")
    }
  }
}

object Phone {
  def loadFromStream(stream: InputStream)(implicit phoneParser: PhoneParser): Iterator[Phone] =
    Source.fromInputStream(stream).getLines().map(phoneParser)

  def parse(raw: String)(implicit phoneParser: PhoneParser) = phoneParser(raw)
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
