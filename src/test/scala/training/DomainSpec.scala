package training

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

trait TrainingSpec extends FlatSpec with Matchers with MockFactory


class PhoneParserSpec extends TrainingSpec {

  behavior of "DefaultPhoneParser"

  val defaultPhoneParser = implicitly[PhoneParser]

  it should "parse a string and turn it into a Phone" in {
    defaultPhoneParser("1,mobile,+44 123 456 777") shouldBe Phone(1, "mobile", "+44 123 456 777")
    defaultPhoneParser("3,mobile,  07767 555 5555") shouldBe Phone(3, "mobile", "07767 555 5555")
  }
}

class PhoneSpec extends TrainingSpec {

  val phone1 = Phone(1, "mobile", "+44 123 456 777")
  val phone2 = Phone(1, "home", "0123 432 222")
  val phone3 = Phone(3, "mobile", "07767 555 5555")

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

class PersonSpec extends TrainingSpec {
  val phil = Person(1, "Phil")
  it should "have an id field and a name field" in { //we wouldn’t normally test getters
    phil.id shouldBe 1
    phil.name shouldBe "Phil"
  }
}