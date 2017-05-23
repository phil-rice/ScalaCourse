package training

trait ToJson{
  def toJson: String
}



object Json{
  def apply(j: ToJson) = j.toJson
}