import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

object AkkaHttpSimple {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("akkaHttpSimpleSystem")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    val route: Route = pathPrefix("ping") {
      path(IntNumber) { i =>
        get {
          complete(s"Int $i")
        }
      } ~ path(LongNumber) { l =>
        get {
          complete(s"Long $l")
        }
      } ~ path(DoubleNumber) { d =>
        get {
          complete(s"Double $d")
        }
      }
    } ~ path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say Hello to Akka Http!</h1>"))
      }
    }


    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}