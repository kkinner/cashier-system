package com.cashier_system.main

/** Used libraries */
import akka.kernel.{ Bootable }
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import collection.mutable.{ ListBuffer }
import com.typesafe.config.{ ConfigFactory }
import scala.util.{ Random }

/** server application
 *
 * creates a new server application
 */
class CashierSystemApplication extends Bootable {
  /** actor system */
  val system = ActorSystem("CashierSystemApplication", ConfigFactory.load.getConfig("cashiersystem"))
  /** local actor */
  val actor = system.actorOf(Props[CashierSystemActor], "CashierSystem")

  /** do server operation   
   *
   *@param op the operation to execute     
   */
  def doOp(op: SystemOp) = {
    actor ! (op)
  }

  /** do something at startup */
  def startup() {
    println("Started Cashier System Application - waiting for clients")
  }

  /** do something at startup */
  def shutdown() {
    /** end program */
    system.shutdown()
  }
}

/** server actor
 *
 * creates a new server actor
 */
class CashierSystemActor extends Actor {
  /** count of clients answered to get balance request */
  var answerFrom:Int = 0
  /** total balance of all client responses */
  var balance:Int = 0
  /** list of all client handles */
  val clients = new ListBuffer[ActorRef] 
  /** connected clients count */
  var clientcount: Int = 0 
  /** count of clients where the get balance request was sent to */
  var sentTo:Int = 0
  
  /** received message handling */
  def receive = {
    /** return count of connected clients */
    case ClientCount() => println("Client count is %d".format(clientcount))
    /** new client is connecting */
    case Register() =>
      println("New client")
      /** save client handle */
      clients += sender
      clientcount += 1
      /** initialize client with some money */
      sender ! Init(1000)
    /** a client unregisters
     *
     * @param value the current balance of the client     
     */
    case Unregister(value) =>
      println("Client unregistered with %d".format(value))
      clients -= sender
      clientcount -= 1
    /** view total balance of all connected clients */
    case ViewTotalBalance() =>
      answerFrom = 0
      balance = 0
      sentTo = 0
      /** loop through the clients and send a get balance request */
      clients foreach {self ! GetBalance(_)}
    /** send a get balance request
     *
     * @param actorRef the client handle         
     */
    case GetBalance(actorRef) =>
      /** ask client to respond the current balance */
      actorRef ! ReturnBalance();
      sentTo += 1
    /** client answer for get balance
     *
     * @param value clients current balance         
     */
    case Balance(value) =>
      balance += value
      answerFrom += 1
      /** print balance when all clients have answered */
      if (sentTo == answerFrom) println("Total balance is %d".format(balance))
  }
}

/** Factory for [[com.cashier_system.main.CashierSystemApp]] instances. */
object CashierSystemApp {
  /** main method */
  def main(args: Array[String]) {
    /** exit clause */
    var doRun:Boolean = true 
    /** create new server instance */
    val app = new CashierSystemApplication
    /** call startup operation */
    app.startup()

    while (doRun) {
      /** sleep for a second the wait for operation responses */
      Thread.sleep(1000)
      println("\nWhat do you wanna do?\nc - client count\nv - view total balance\ne - exit the app\nYour choice?")
      /** read from STDIN */
      val line = Console.readLine
      /** what did the user enter? */
      line match {
        /** he wants to see the count of connected clients */
        case "c" => app.doOp(ClientCount())            
        /** he wants to view the total balance of all connected clients */
        case "v" => app.doOp(ViewTotalBalance())            
        /** he wants to exit */
        case "e" =>
          println("exit app")
          doRun = false
      }      
    }

    /** call shutdown operation */
    app.shutdown()
  }
}
