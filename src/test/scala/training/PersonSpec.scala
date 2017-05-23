package training


class PersonParserSpec extends TrainingSpec with PhoneFixture {
  behavior of "DefaultPersonParser"
  val defaultPersonParser = implicitly[PersonParser]

  it should "parse people" in {
    defaultPersonParser("1,Phil", phoneMap) shouldBe Person(1, "Phil", List(phone1, phone2))
  }
  it should "throw an error if it cannot parse the string" in {
    intercept[PersonParserException](defaultPersonParser("some invalid text", phoneMap)).getMessage shouldBe "Cannot parse some invalid text"
  }
}

class PersonSpec extends TrainingSpec with PhoneFixture {
  behavior of "Person.scala.parse"

  val person1 = Person(1, "Phil", List(phone1, phone2))
  val person2 = Person(2, "Bob", List())
  val person3 = Person(3, "Jill", List(phone3))

  it should "delegate to PersonParser" in {
    implicit val phoneParser = mock[PersonParser]
    (phoneParser.apply _) expects ("one", phoneMap) returns person1

    Person.parse("one", phoneMap) shouldBe person1
  }
}