name := "play_Client_with_jar"

version := "1.0"

lazy val `play_client_with_jar` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(jdbc, cache, ws, specs2 % Test)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"


routesGenerator := InjectedRoutesGenerator

resolvers += "Gluu repository" at "http://ox.gluu.org/maven"

libraryDependencies += "org.xdi" % "oxd-java" % "2.4.4.Final"


libraryDependencies += "oxd.play.java" % "oxd-play" % "2.4.4-FINAL"








fork in run := true