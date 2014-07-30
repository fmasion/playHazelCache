name := "playHazelCache"

version := "3.2.3"

libraryDependencies ++= Seq(
    cache,
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.3-play2.2",
    "playhazelcastclient"  % "playhazelcastclient_2.10" % "3.2.3-play2.2"
)     

resolvers += Resolver.url("Fred's GitHub Play Repository", url("http://fmasion.github.com/releases/"))(Resolver.ivyStylePatterns)


play.Project.playScalaSettings
