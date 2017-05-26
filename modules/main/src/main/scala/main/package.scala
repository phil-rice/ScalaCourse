import domain.{Person, Phone}
import json.ToJson

package object main {

  implicit object ToJsonForPhone extends ToJson[Phone] {
    def apply(phone: Phone) = {
      import phone._
      s"""{"type": "$phoneType", "number": "$number"}"""
    }
  }

  implicit def toJsonForPerson(implicit toJsonForPhone: ToJson[Phone]) = new ToJson[Person] {
    def apply(person: Person) = {
      import person._
      s"""{"name": "$name", "phones": ${phones.map(toJsonForPhone).mkString("[", ",", "]")}}"""
    }
  }
}
