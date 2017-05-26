package training

trait FindId[K, V] extends (V => K)

object Maps {
  def add[K, V](map: Map[K, List[V]], value: V)(implicit findIntId: FindId[K, V]): Map[K, List[V]] = {
    val id = findIntId(value)
    map.get(id) match {
      case Some(list) => map + (id -> (list :+ value))
      case None => map + (id -> List(value))
    }
  }

  def emptyMapOfLists[K, V] = Map[K, List[V]]()

  def makeFromList[K, V](list: Seq[V])(implicit findId: FindId[K, V]) =
    list.foldLeft(emptyMapOfLists[K,V])(Maps.add)

  implicit class MapPimper[K, V](map: Map[K, List[V]]) {
    def +(v: V)(implicit findId: FindId[K, V]) = Maps.add(map, v)
  }

  implicit class ListPimper[V](list: List[V]){
    def toMapOfLists[K](implicit findId: FindId[K, V]) = makeFromList[K,V](list)
  }
  implicit class IteratorPimper[V](iterator: Iterator[V]){
    def toMapOfLists[K](implicit findId: FindId[K, V]) = makeFromList[K,V](iterator.toList)
  }
}
