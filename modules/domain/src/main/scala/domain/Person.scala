package domain

import java.io.InputStream

import scala.io.Source


case class Person(id: Int, name: String, phones: List[Phone] = List())

object Person {
  implicit def toJsonForPerson(implicit toJsonForPhone: ToJson[Phone]) = new ToJson[Person] {
    def apply(person: Person) = {
      import person._
      s"""{"name": "$name", "phones": ${phones.map(toJsonForPhone).mkString("[", ",", "]")}}"""
    }
  }

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