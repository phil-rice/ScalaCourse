package training

case class Person(id: Int, name: String, phones: List[Phone] = List())

object Person {
  def parse(raw: String, phoneMap: PhoneMap)(implicit personParser: PersonParser) = personParser(raw, phoneMap)
}

trait PersonParser extends ((String,PhoneMap) => Person)

object PersonParser {

  implicit object DefaultPersonParser extends PersonParser {
    override def apply(raw: String, phoneMap:PhoneMap): Person = raw.split(",") match {
      case Array(id, name) => Person(id.toInt, name, phoneMap.getOrElse(id.toInt, List()))
      case _ => throw new PersonParserException(s"Cannot parse $raw")
    }
  }
}