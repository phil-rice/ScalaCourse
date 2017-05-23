package training

object Maps {
  def add(map: PhoneMap, p: Phone): PhoneMap =
    map.get(p.personId) match {
      case Some(list) => map + (p.personId -> (list :+ p))
      case None => map + (p.personId -> List(p))
    }
}
