name := "play-example"

version := "1.0"

lazy val `play-example` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs )

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.2.2"

libraryDependencies += "com.google.code.gson" % "gson" % "2.6.2"


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  