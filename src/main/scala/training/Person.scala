package training

import java.io.InputStream

import scala.io.Source


case class Person(id: Int, name: String, phones: List[Phone] = List()) extends ToJson {
  def toJson = s"""{"name": "$name", "phones": ${phones.map(_.toJson).mkString("[", ",", "]")}}"""
}


object Person {
  def loadFromStream(phoneMap: PhoneMap)(stream: InputStream)(implicit personParser: PersonParser): Iterator[Person] =
    Source.fromInputStream(stream).getLines().map(personParser(phoneMap))

  def parse(phoneMap: PhoneMap)(raw: String)(implicit personParser: PersonParser) = personParser(phoneMap)(raw)
}

trait PersonParser extends {
  def apply(phoneMap: PhoneMap)(raw: String)(implicit personParser: PersonParser): Person
}

object PersonParser {

  implicit object DefaultPersonParser extends PersonParser {
    override def apply(phoneMap: PhoneMap)(raw: String)(implicit personParser: PersonParser): Person = raw.split(",") match {
      case Array(id, name) => Person(id.toInt, name, phoneMap.getOrElse(id.toInt, List()))
      case _ => throw new PersonParserException(s"Cannot parse $raw")
    }
  }

}