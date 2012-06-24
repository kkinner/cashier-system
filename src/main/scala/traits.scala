package com.cashier_system.main

/** Used libraries */
import akka.kernel.{ Bootable }
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import collection.mutable.{ ListBuffer }
import com.typesafe.config.{ ConfigFactory }
import scala.util.{ Random }

/** server operations
 *
 * There are several classes defined what are used by the server system:
 *   ClientCount() - returns the count of registered clients
 *   Register() - is called when a new client connects
 *   Unregister(value:Int) - is called when a client disconnects - the client provides the current amout of money
 *     value - clients current balance 
 *   Balance(value:Int) - is called by the client to submit the current balance
 *     value - clients current balance
 *   GetBalance(actorRef:ActorRef) - sends a request to a specified client to get the clients current balance
 *     actorRef - reference for a client to connect to 
 *   ViewTotalBalance() - sends a GetBalance for all registered clients     
 */
trait SystemOp
case class ClientCount() extends SystemOp
case class Register() extends SystemOp
case class Unregister(value:Int) extends SystemOp
case class Balance(value:Int) extends SystemOp
case class GetBalance(actorRef:ActorRef) extends SystemOp
case class ViewTotalBalance() extends SystemOp

/** client operations
 *
 * There are several classes defined what are used by the client system:
 *   Init(value:Int) - is called by the server to setup the client with a specified amount of money
 *     value - initial balance
 *   ReturnBalance() - is called by the server to retrieve the clients current balance
 *   ViewBalance() - returns the clients current balance
 */
trait ClientOp
case class Init(value:Int) extends ClientOp
case class ReturnBalance() extends ClientOp
case class ViewBalance() extends ClientOp

/** math operations
 *
 * There are several classes defined what are used by the client to manipulate the clients balance:
 *   Add(value:Int) - adds an user defined amount of money
 *   Sub(value:Int) - substracts an user defined amount of money
 */
trait MathOp
case class Add(value:Int) extends MathOp
case class Sub(value:Int) extends MathOp
