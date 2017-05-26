package main

import domain.PersonFixture

class PersonJsonSpec extends TrainingSpec with PersonFixture {

  behavior of "Json with 'Person'"
  it should "turn a person into a Json string" in {

    person1.toJson shouldBe """{"name": "Phil", "phones": [{"type": "mobile", "number": "+44 123 456 777"},{"type": "home", "number": "0123 432 222"}]}"""
    person2.toJson shouldBe """{"name": "Bob", "phones": []}"""
    person3.toJson shouldBe """{"name": "Jill", "phones": [{"type": "mobile", "number": "07767 555 5555"}]}"""
  }
}
