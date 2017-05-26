package training

import training.Maps._

class MapsSpec extends TrainingSpec  {

  case class TestClassForMaps(id: Int)
  object TestClassForMaps{
    implicit object FindIdForTestClassForMaps extends FindId[Int, TestClassForMaps]{
      override def apply(t: TestClassForMaps): Int = t.id
    }
  }

  val (test1, test2, test3) = (TestClassForMaps(1), TestClassForMaps(1), TestClassForMaps(3))

  behavior of "Maps.add"
  val emptyMap = Map[Int, List[TestClassForMaps]]()

  it should "allow a person to be added to the empty map" in {
    emptyMap + test1 shouldBe Map(1 -> List(test1))
  }
  it should "allow multiple phones to be added" in {
    emptyMap + test1 + test2 shouldBe Map(1 -> List(test1, test2))
  }

  behavior of "Maps.makeFromList"
  it should "make a map from a list" in {
    List(test1, test2, test3).toMapOfLists[Int] shouldBe  Map(1 -> List(test1, test2), 3 -> List(test3))
  }

}
