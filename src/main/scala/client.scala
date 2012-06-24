package com.cashier_system.main

/** Used libraries */
import akka.kernel.{ Bootable }
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import collection.mutable.{ ListBuffer }
import com.typesafe.config.{ ConfigFactory }
import scala.util.{ Random }

/** client application
 *
 * creates a new client application
 */
class CashierClientApplication extends Bootable {
  /** actor system */
  val system = ActorSystem("CashierClientApplication", ConfigFactory.load.getConfig("cashierclient"))
  /** local actor */
  val actor = system.actorOf(Props[CashierClientActor], "lookupActor")
  /** remote actor */
  val remoteActor = system.actorFor("akka://CashierSystemApplication@127.0.0.1:2552/user/CashierSystem")

  /** do client operation   
   *
   *@param op the operation to execute     
   */
  def doOp(op: ClientOp) = {
    /** send message to local actor */
    actor ! op
  }

  /** do calculation operation   
   *
   *@param op the calculation to execute     
   */
  def doCalc(op: MathOp) = {
    /** send message to local actor */
    actor ! op
  }

  /** do something at startup */
  def startup() {
    println("Started Cashier Client Application")
    /** call server operation Register() on local actor */
    actor ! (remoteActor, Register())
  }

  /** do something at startup */
  def shutdown() {
    /** call server operation Unregister() on local actor */
    actor ! (remoteActor, Unregister(0))
    /** end program */
    system.shutdown()
  }
}

/** client actor
 *
 * creates a new client actor
 */
class CashierClientActor extends Actor {
  /** clients balance */
  var money:Int = 0

  /** received message handling */
  def receive = {
    /** remote actor call
     *
     * @param actor the remote actor to send the message to
     * @param op the server operation to call     
     */
    case (actor: ActorRef, op: SystemOp) ⇒ op match {
      case Register() => actor ! Register()
      case Unregister(value) => actor ! Unregister(money)
    }
    /** math operation execution
     *
     * @param op the math operation to execute     
     */
    case (op: MathOp) ⇒ op match {
      /** add operation
       *
       * @param value the value to add to the balance     
       */
      case Add(value) =>
        println("added %d".format(value))
        money += value 
      /** substract operation
       *
       * @param value the value to substract from the balance     
       */
      case Sub(value) =>
        println("substracted %d".format(value))
        money -= value 
    }
    /** client operation call */
    case result: ClientOp => result match {
      /** initialization operation
       *
       * @param value the initial balance     
       */
      case Init(value) => money = value
      /** return the balance operation */
      case ReturnBalance() =>
        println("Request to return balance")
        sender ! Balance(money) 
      /** show the current balance operation */
      case ViewBalance() => println("Current balance is %d".format(money))
    }
  }
}

/** Factory for [[com.cashier_system.main.CashierClientApp]] instances. */
object CashierClientApp {
  /** main method */
  def main(args: Array[String]) {
    /** exit clause */
    var doRun:Boolean = true 
    /** create new client instance */
    val app = new CashierClientApplication
    /** call startup operation */
    app.startup()

    /** loop application */
    while (doRun) {
      /** sleep for a second the wait for operation responses */
      Thread.sleep(1000)
      println("\nWhat do you wanna do?\na - add money\ns - sub money\nv - view balance\ne - exit the app\nYour choice?")
      /** read from STDIN */
      val line = Console.readLine
      /** what did the user enter? */
      line match {
        /** he wants to add money to the balance */
        case "a" =>
          println("How much money you wanna add?")
          app.doCalc(Add(Console.readInt))            
        /** he wants to substract money to the balance */
        case "s" =>
          println("How much money you wanna sub?")
          app.doCalc(Sub(Console.readInt))            
        /** he wants to view the balance */
        case "v" =>
          app.doOp(ViewBalance())            
        /** he wants exit the client */
        case "e" =>
          println("exit app")
          doRun = false
      }      
    }
    
    /** call shutdown operation */
    app.shutdown()
  }
}
