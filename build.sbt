import Dependencies._

lazy val commonSettings = Seq(
  name := "Scala-Kafka",
  organization := "org.gcbh.scala_kafka",
  version := "0.1.0",
  // set the Scala version used for the project
  scalaVersion := "2.12.4"
)

lazy val scala_kafka = (project in file(".")).
  settings(commonSettings,
    libraryDependencies ++= Seq(
      Kafka,
      TwitterUtil("collection"),
      Cats("core"),
      Catbird("finagle"),
      Java8),
    // Test dependencies
    libraryDependencies ++= Seq(
      ScalaTest % Test,
      Mockito,
      EmbeddedKafka
    )
  )