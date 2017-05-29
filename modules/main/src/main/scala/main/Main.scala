package main

import java.io.InputStream

import domain.{Person, Phone}
import json.Json._
import training.Maps._

object Main extends App {
  implicit def stringToStream(name: String): InputStream = getClass.getClassLoader.getResourceAsStream(name)

  val phoneMap = Phone.loadFromStream("phone.csv").toMapOfLists
  val people = Person.loadFromStream(phoneMap)("people.csv")
  people.map(_.toJson).foreach(println)
}


