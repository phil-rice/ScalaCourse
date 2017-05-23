package training

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

trait TrainingSpec extends FlatSpec with Matchers with MockFactory

trait PhoneFixture {
  val phone1 = Phone(1, "mobile", "+44 123 456 777")
  val phone2 = Phone(1, "home", "0123 432 222")
  val phone3 = Phone(3, "mobile", "07767 555 5555")

  val phoneMap = Map(1 -> List(phone1, phone2), 3 -> List(phone3))
}

class PhoneParserSpec extends TrainingSpec {

  behavior of "DefaultPhoneParser"

  val defaultPhoneParser = implicitly[PhoneParser]

  it should "parse a string and turn it into a Phone" in {
    defaultPhoneParser("1,mobile,+44 123 456 777") shouldBe Phone(1, "mobile", "+44 123 456 777")
    defaultPhoneParser("3,mobile,  07767 555 5555") shouldBe Phone(3, "mobile", "07767 555 5555")
  }
}

class PhoneSpec extends TrainingSpec with PhoneFixture {

  behavior of "Phone.parse"

  it should "delegate to PhoneParser" in {
    implicit val phoneParser = stub[PhoneParser]
    (phoneParser.apply _) when "one" returns phone1

    Phone.parse("one") shouldBe phone1
  }

  behavior of "Phone.loadFromFile"

  it should "Load from stream, using the parser" in {
    val stream = getClass.getClassLoader.getResourceAsStream("csvForTest.csv")
    implicit val phoneParser = stub[PhoneParser]
    (phoneParser.apply _) when "one" returns phone1
    (phoneParser.apply _) when "two" returns phone2
    (phoneParser.apply _) when "three" returns phone3

    Phone.loadFromStream(stream).toList shouldBe List(phone1, phone2, phone3)
  }
}

class PersonParserSpec extends TrainingSpec with PhoneFixture {
  behavior of "DefaultPersonParser"
  val defaultPersonParser = implicitly[PersonParser]

  it should "parse people" in {
    defaultPersonParser("1,Phil", phoneMap) shouldBe Person(1, "Phil", List(phone1, phone2))
  }
}

class PersonSpec extends TrainingSpec with PhoneFixture {
  behavior of "Person.parse"

    val person1 = Person(1, "Phil", List(phone1, phone2))
    val person2 = Person(2, "Bob", List())
    val person3 = Person(3, "Jill", List(phone3))

  it should "delegate to PersonParser" in {
    implicit val phoneParser = stub[PersonParser]
    (phoneParser.apply _) when("one", phoneMap) returns person1

    Person.parse("one", phoneMap) shouldBe person1
  }
}