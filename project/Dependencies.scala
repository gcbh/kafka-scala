import sbt._

object Dependencies {

  final val dependencies: Map[String, (String) => ModuleID] = Map(
    "java8" -> { _ => "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0" },
    "scalaTest" -> { _ => "org.scalatest" %% "scalatest" % "3.0.3" },
    "kafka" -> { _ => "org.apache.kafka" %% "kafka" % "1.0.0" },
    "embedded-kafka" -> { _ => "net.manub" %% "scalatest-embedded-kafka" % "0.15.0" },
    "twitter-util" -> { name => "com.twitter" %% s"util-$name" % "17.11.0" },
    "cats" -> { name => "org.typelevel" %% s"cats-$name" % "1.0.0-RC1" },
    "catbird" -> { name => "io.catbird" %% s"catbird-$name" % "0.21.0" },
    "mockito" -> { _ => "org.mockito" % "mockito-core" % "2.6.2" }
  )

  def Kafka: ModuleID = dependencies("kafka")("")
  def TwitterUtil(module: String): ModuleID = dependencies("twitter-util")(module)
  def Java8: ModuleID = dependencies("java8")("")
  def Cats(module: String): ModuleID = dependencies("cats")(module)
  def Catbird(module: String): ModuleID = dependencies("catbird")(module)

  def ScalaTest: ModuleID = dependencies("scalaTest")("")
  def EmbeddedKafka: ModuleID = dependencies("embedded-kafka")("")
  def Mockito: ModuleID = dependencies("mockito")("")

  object github { }
}
