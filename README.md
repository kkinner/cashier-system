README for the Scala application "cashier system"

"Cashier system" is a small application to show how Akka remote actors work.
The server is listening for clients and initialize them with some money.
The server is also able to get the total balance from all connected clients.
The clients registers at the server to get the initial balance. The balance can be manipulated by the user via STDIN.
Also the client is able to show it's balance. 

The application is split into three Scala files:
1) the server application
2) the client application
3) the traits definition

To run the application:
1) move to the cashier system root directory
2) execute the simble build tool "sbt"
3) after sbt has started enter the command "compile"
4) enter the command "run"
5) execute the software
  a) on the server -> com.cashier_system.main.CashierSystemApp
  b) on the client -> com.cashier_system.main.CashierClietApp
   
Prerequisites:
Scala 2.9.1
Akka 2.0.2
sbt 0.13
Internet connection

Package contents (sbt standard schema):
+ build.sbt - sbt build file containing library dependencies
+ README - this readme file
+ src[] - source code directory
+++ main[]
+++++ scala[]
+++++++ client.scala - client application
+++++++ server.scala - server application
+++++++ traits.scala - trait definitions  