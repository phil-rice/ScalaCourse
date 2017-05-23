package training

object HelloWorld extends App{
  println(Person(1, "Phil"))
  println(Person(id=1,name= "Phil"))        // we can name parameters for clarity
  println(new Person(1, "Phil"))            // we can use new if we want to
  val person = Person(1, "Phil")           // observe how we define variables
  println(s"name is ${person.name}")       // we don’t use get when accessing fields
  val copy = person.copy(id=2,name="Bob")  // copy is created for us
}

