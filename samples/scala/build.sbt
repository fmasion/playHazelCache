name := "playHazelCache-scala-sample"

version := "0.1.0"

resolvers += Resolver.url("Fred's GitHub Play Repository", url("http://fmasion.github.com/releases/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
    "playhazelcast"  % "playhazelcast_2.10" % "0.2.0",
    "playhazelcache"  % "playhazelcache_2.10" % "0.1.0",
    cache
)     

play.Project.playScalaSettings
