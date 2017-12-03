import sbt._
import Keys._

object Resolvers {
  resolvers += Resolver.typesafeRepo("releases")
  resolvers += Resolver.typesafeIvyRepo("releases")
}
