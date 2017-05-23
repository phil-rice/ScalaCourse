package training

object HelloWorld extends App {
  println(Person(1, "Phil"))
  println(Person(id = 1, name = "Phil")) // we can name parameters for clarity
  println(new Person(1, "Phil")) // we can use new if we want to
  val person = Person(1, "Phil", List(Phone(1, "home", "0123 432 222"))) // observe how we define variables
  println(s"name is ${person.name}") // we don’t use get when accessing fields
  val copy = person.copy(id = 2, name = "Bob") // copy is created for us

  person match {
    case Person(id, name, list) =>    // id, name and list are values we can use
    case Person(_, name, _) =>        // we don’t care about the id or list, so only the name is available
    case Person(id, name, List(phone1)) => //only matches if there is a single phone, and it is in variable phone1
    case _ =>                         // matches everything. Like ‘else’
    case Person(_, _, List(phone1, phone2)) => // matches if there are exactly two phones
    case Person(id, name, List(Phone(_, type1, num1), Phone(_, type2, num2))) =>
  }

  val p@Person(id, name, list) = person
}

