package domain

import java.io.InputStream

import training.FindId

import scala.io.Source


case class Phone(personId: Int, phoneType: String, number: String)

object Phone {
  implicit object ToJsonForPhone extends ToJson[Phone] {
    def apply(phone: Phone) = {
      import phone._
      s"""{"type": "$phoneType", "number": "$number"}"""
    }
  }

  implicit object FindIdForPhone extends FindId[Int, Phone] {
    override def apply(p: Phone): Int = p.personId
  }

  def loadFromStream(stream: InputStream)(implicit phoneParser: PhoneParser): Iterator[Phone] =
    Source.fromInputStream(stream).getLines().map(phoneParser)

  def parse(raw: String)(implicit phoneParser: PhoneParser) = phoneParser(raw)
}

trait PhoneParser extends (String => Phone)

object PhoneParser {

  implicit object DefaultPhoneParser extends PhoneParser {
    def apply(raw: String) = raw.split(",") match {
      case Array(id, phoneType, number) => Phone(id.toInt, phoneType, number.trim)
      case _ => throw new PhoneParserException(s"Cannot parse $raw")
    }
  }

}