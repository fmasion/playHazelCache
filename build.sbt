name := "playHazelCache"

version := "2.6.7"

libraryDependencies ++= Seq(
    cache,
    "playhazelcast"  % "playhazelcast_2.10" % "2.6.7",
    "playhazelcastclient"  % "playhazelcastclient_2.10" % "2.6.7"
)     

  resolvers += Resolver.url("Fred's GitHub Play Repository", url("http://fmasion.github.com/releases/"))(Resolver.ivyStylePatterns)


  play.Project.playScalaSettings
