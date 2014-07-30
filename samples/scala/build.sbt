name := "playHazelCache-scala-sample"

version := "3.2.3-play2.2"

resolvers += "bintray" at "http://dl.bintray.com/fmasion/maven"

libraryDependencies ++= Seq(
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.3-play2.2",
    "playhazelcache"  % "playhazelcache_2.10" % "3.2.3-play2.2",
    cache
)     

play.Project.playScalaSettings
