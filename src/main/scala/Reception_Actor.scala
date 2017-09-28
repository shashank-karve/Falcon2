import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.actor.Props

import scala.io.StdIn

object Reception_Actor {
  def main(args: Array[String]) {
      implicit val system = ActorSystem("falcon")
      implicit val materializer = ActorMaterializer()
      implicit val executionContext = system.dispatcher

      val r = scala.util.Random
      val teamer = system.actorOf(Props[TeamAssign_Actor], name="team_assign")

    var decision = 0
    val route =
      path("test") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              "<h2>Seems to work fine!</h2>"
            )
          )
        }
      }  ~
    get{
        pathPrefix("challenge" / LongNumber) {

          id =>
            val all = if(id.toString.isEmpty()){"<undefined>"}

            if (all == "<undefined>")
            {
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Please specify an id 404 <h1>"))

            }
            else
            {
              //teamer ! "AssignTeam"
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,s"<h2>You are now assigned to team # ${r.nextInt()}</h2>"))
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,s"<p><h3>Your Team Members are :-</h3></p>"))

            }


        }


      } ~
    path("challenge") {
      get{
        complete(HttpEntity(
          ContentTypes.`text/html(UTF-8)`,"<h2>All challenges here </h2>"

        )
        )

      }
    }



      //teamer ! AssignTeam
      val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
      println(s"Falcon's up and runnign on localhost \n Press RETURN to stop..\n")
      StdIn.readLine() //keep the actor running till user hits return

      bindingFuture
                    .flatMap(_.unbind()) //trigger unbinding
                    .onComplete(_ => system.terminate()) //shutdown when done
  }

}
