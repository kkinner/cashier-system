README for the Scala application "cashier system"<br><br>
"Cashier system" is a small application to show how Akka remote actors work.<br>
The server is listening for clients and initialize them with some money.<br>
The server is also able to get the total balance from all connected clients.<br>
The clients registers at the server to get the initial balance. The balance can be manipulated by the user via STDIN.<br>
Also the client is able to show it's balance.<br><br>
The application is split into three Scala files:<br>
1) the server application<br>
2) the client application<br>
3) the traits definition<br><br>
To run the application:<br>
1) move to the cashier system root directory<br>
2) execute the simble build tool "sbt"<br>
3) after sbt has started enter the command "compile"<br>
4) enter the command "run"<br>
5) execute the software<br>
  a) on the server -> com.cashier_system.main.CashierSystemApp<br>
  b) on the client -> com.cashier_system.main.CashierClietApp<br><br>
Prerequisites:<br>
Scala 2.9.1<br>
Akka 2.0.2<br>
sbt 0.13<br>
Internet connection<br><br>
Package contents (sbt standard schema):<br>
+ build.sbt - sbt build file containing library dependencies<br>
+ README - this readme file<br>
+ src[] - source code directory<br>
+++ main[]<br>
+++++ resources[]<br>
+++++++ application.conf<br>
+++++++ common.conf<br>
+++++ scala[]<br>
+++++++ client.scala - client application<br>
+++++++ server.scala - server application<br>
+++++++ traits.scala - trait definitions  