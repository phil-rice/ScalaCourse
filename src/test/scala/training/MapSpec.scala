package training

import training.Maps._

class MapsSpec extends TrainingSpec with PhoneFixture {

  behavior of "Maps.add"
  val emptyMap = Map[Int, List[Phone]]()

  it should "allow a person to be added to the empty map" in {
    emptyMap + phone1 shouldBe Map(1 -> List(phone1))
  }
  it should "allow multiple phones to be added" in {
    emptyMap + phone1 + phone2 shouldBe Map(1 -> List(phone1, phone2))
  }
  behavior of "Maps.makeFromList"

  it should "make a map from a list" in {
    List(phone1, phone2, phone3).toMapOfLists[Int] shouldBe  Map(1 -> List(phone1, phone2), 3 -> List(phone3))
  }

}
