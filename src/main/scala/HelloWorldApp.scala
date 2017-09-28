import akka.actor.{ Actor, ActorRef, Props, ActorSystem }
import scala.io.StdIn

object HelloWorldApp {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("hello-world-actor-system")
    try {
      // Create hello world actor
      val helloWorldActor: ActorRef = system.actorOf(Props[HelloWorldActor], "HelloWorldActor")
      // Send message to actor
      helloWorldActor ! "World"
      // Exit the system after ENTER is pressed
      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }
}

class HelloWorldActor extends Actor {
  def receive = {
    case msg: String => println(s"Hello $msg")
  }
}