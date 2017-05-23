package training


class PersonJsonSpec extends TrainingSpec with PersonFixture {

  behavior of "Json with 'Person'"
  it should "turn a person into a Json string" in {

    Json(person1) shouldBe """{"name": "Phil", "phones": [{"type": "mobile", "number": "+44 123 456 777"},{"type": "home", "number": "0123 432 222"}]}"""
    Json(person2) shouldBe """{"name": "Bob", "phones": []}"""
    Json(person3) shouldBe """{"name": "Jill", "phones": [{"type": "mobile", "number": "07767 555 5555"}]}"""
  }
}
