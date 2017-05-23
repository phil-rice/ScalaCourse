package training

case class Person(id: Int, name: String, phones: List[Phone] = List())

case class Phone(personId: Int, phoneType: String, number: String)

object Phone {
  def parse(s: String): String = ???
}
