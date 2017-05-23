package training


trait PersonFixture extends PhoneFixture{

  val person1 = Person(1, "Phil", List(phone1, phone2))
  val person2 = Person(2, "Bob", List())
  val person3 = Person(3, "Jill", List(phone3))
}
class PersonParserSpec extends TrainingSpec with PhoneFixture {
  behavior of "DefaultPersonParser"
  val defaultPersonParser = implicitly[PersonParser].apply(phoneMap) _

  it should "parse people" in {
    defaultPersonParser("1,Phil") shouldBe Person(1, "Phil", List(phone1, phone2))
  }
  it should "throw an error if it cannot parse the string" in {
    intercept[PersonParserException](defaultPersonParser("some invalid text")).getMessage shouldBe "Cannot parse some invalid text"
  }
}

class PersonSpec extends TrainingSpec with PersonFixture {
  behavior of "Person.parse"


  it should "delegate to PersonParser" in {
    implicit val phoneParser = mock[PersonParser]
    (phoneParser.apply(_: PhoneMap)(_: String)(_: PersonParser)).expects(phoneMap, "one", phoneParser).returns(person1)
    Person.parse(phoneMap)("one") shouldBe person1
  }

  behavior of "Person.loadFromStream"

  it should "load people from a file using the personParser" in {
    val stream = getClass.getClassLoader.getResourceAsStream("csvForTest.csv")
    implicit val personParser = mock[PersonParser]
    (personParser.apply(_: PhoneMap)(_: String)(_: PersonParser)).expects(phoneMap, "one", personParser).returns(person1)
    (personParser.apply(_: PhoneMap)(_: String)(_: PersonParser)).expects(phoneMap, "two", personParser).returns(person2)
    (personParser.apply(_: PhoneMap)(_: String)(_: PersonParser)).expects(phoneMap, "three", personParser).returns(person3)

    Person.loadFromStream(phoneMap)(stream).toList shouldBe List(person1, person2, person3)
  }
}