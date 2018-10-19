name := "ReserveMyResturant"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.typelevel" %% "cats-core" % "1.2.0",
  "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test,
  "org.scalamock" %% "scalamock" % "4.1.0" % Test,
  "com.typesafe.akka" %% "akka-stream" % "2.5.17",
  "com.typesafe.akka" %% "akka-actor" % "2.5.17",
  "com.github.finagle" %% "finch-core" % "0.22.0",
  "com.github.finagle" %% "finch-circe" % "0.22.0",
  "com.github.finagle" %% "finch-generic" % "0.22.0",
  "io.circe" %% "circe-generic" % "0.9.0",
  "io.circe" %% "circe-parser" % "0.9.1",
  "io.circe" %% "circe-optics" % "0.9.1",
  "com.twitter" %% "twitter-server" % "18.9.1",

)