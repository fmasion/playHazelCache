name := "playHazelCache"

version := "0.1.0"

libraryDependencies ++= Seq(
    cache,
    "playhazelcast"  % "playhazelcast_2.10" % "0.2.0",
    "playhazelcastclient"  % "playhazelcastclient_2.10" % "0.2.0"
)     

  resolvers += Resolver.url("Fred's GitHub Play Repository", url("http://fmasion.github.com/releases/"))(Resolver.ivyStylePatterns)


  play.Project.playScalaSettings
