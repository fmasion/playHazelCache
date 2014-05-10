import play.Project._

name := "playHazelCache"

version := "3.2.1"

libraryDependencies ++= Seq(
    cache,
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.1",
    "playhazelcastclient"  % "playhazelcastclient_2.10" % "3.2.1"
)     

  resolvers += Resolver.file("Local repo", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns)

  play.Project.playScalaSettings
