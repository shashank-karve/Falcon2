import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn



class TeamAssign_Actor(challenge: Long) extends Actor {
  //This actor should check if the challenge is accepting more teams and if yes
  // randomly generate a team

  val team_mate1 = "Daniel"
  val team_mate2 = "Kislay"
  val team_mate3 = "Javian"

  val r = scala.util.Random

  def receive = {
    case "AssignTeam" => {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,s"<h2>You are now assigned to team # ${r.nextInt()}</h2>"))
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,s"<p><h3>Your Team Members are :-</h3></p>"))
    }
  }

}

