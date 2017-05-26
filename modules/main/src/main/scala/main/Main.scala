package main

import java.io.InputStream

import domain.{Person, Phone}
import json.Json
import training.Maps._

object Main extends App {
  implicit def stringToStream(name: String): InputStream = getClass.getClassLoader.getResourceAsStream(name)

  val phoneMap = Phone.loadFromStream("").toMapOfLists
  val people = Person.loadFromStream(phoneMap)("")
  people.foreach(_.toJson)
}
