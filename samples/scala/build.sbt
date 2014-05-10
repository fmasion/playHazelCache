import play.Project._

name := "playHazelCache-scala-sample"

version := "3.2.1"

resolvers += Resolver.url("Fred's GitHub Play Repository", url("http://fmasion.github.com/releases/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.1",
    "playhazelcache"  % "playhazelcache_2.10" % "3.2.1",
    cache
)     

  resolvers += Resolver.file("Local repo", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns)

play.Project.playScalaSettings
