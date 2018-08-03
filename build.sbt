name := "ReserveMyResturant"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.3",
  "org.typelevel" %% "cats-core" % "1.2.0"
)