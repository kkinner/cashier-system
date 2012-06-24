name := "Cashier system"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "akka-actor-2.0.2" from "http://repo.typesafe.com/typesafe/snapshots/com/typesafe/akka/akka-actor/2.0.2/akka-actor-2.0.2.jar" 

libraryDependencies += "com.typesafe.akka" % "akka-kernel" % "akka-kernel-2.0.2" from "http://repo.typesafe.com/typesafe/snapshots/com/typesafe/akka/akka-kernel/2.0.2/akka-kernel-2.0.2.jar"

libraryDependencies += "com.typesafe.akka" % "akka-remote" % "akka-remote-2.0.2" from "http://repo.typesafe.com/typesafe/snapshots/com/typesafe/akka/akka-remote/2.0.2/akka-remote-2.0.2.jar"

libraryDependencies += "maven2.io.netty" % "netty" % "netty-3.3.0.Final" from "http://mirrors.ibiblio.org/pub/mirrors/maven2/io/netty/netty/3.3.0.Final/netty-3.3.0.Final.jar"

libraryDependencies += "com.google.protobuf" % "protobuf-java" % "protobuf-java-2.4.1" from "http://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/2.4.1/protobuf-java-2.4.1.jar"
