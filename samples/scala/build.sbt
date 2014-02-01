name := "playHazelCache-scala-sample"

version := "2.6.6"

resolvers += Resolver.url("Fred's GitHub Play Repository", url("http://fmasion.github.com/releases/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
    "playhazelcast"  % "playhazelcast_2.10" % "2.6.6",
    "playhazelcache"  % "playhazelcache_2.10" % "2.6.6",
    cache
)     

play.Project.playScalaSettings
