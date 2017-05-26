package training

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
class HelloFutures {
  val future = Future(5).map(_ + 2).foreach(println)


}
