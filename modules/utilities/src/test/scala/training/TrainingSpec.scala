package training

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.Eventually
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits._

trait TrainingSpec extends FlatSpec with Matchers with MockFactory with Eventually {

  implicit class FuturePimper[X](f: Future[X]) {
    def await5s = Await.result(f, 5 seconds)
  }
}

class ExampleTest extends TrainingSpec {
  behavior of "Example"
  it should "add 2 to the parameter" in {
    Future(5).map(_ + 2).await5s shouldBe 7
  }
}