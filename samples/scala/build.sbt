name := "playHazelCache-scala-sample"

version := "3.2.3-play2.3"

resolvers += "bintray" at "http://dl.bintray.com/fmasion/maven"

libraryDependencies ++= Seq(
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.3-play2.3",
    "playhazelcache"  % "playhazelcache_2.10" % "3.2.3-play2.3",
    cache
)     

lazy val playHazelCache-scala-sample = (project in file(".")).enablePlugins(PlayScala)
