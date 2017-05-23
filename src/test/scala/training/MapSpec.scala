package training


class MapsSpec extends TrainingSpec with PhoneFixture{

  behavior of "Maps.add"
  val emptyMap=Map[Int,List[Phone]]()

  it should "allow a person to be added to the empty map" in {
    Maps.add(emptyMap, phone1) shouldBe Map(1 -> List(phone1))
  }
  it should "allow multiple phones to be added" in {
    Maps.add(Maps.add(emptyMap, phone1), phone2) shouldBe Map(1 -> List(phone1, phone2))
  }
}
