name := "playHazelCache"

version := "3.2.3-play2.3"

libraryDependencies ++= Seq(
    cache,
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.3-play2.3",
    "playhazelcastclient"  % "playhazelcastclient_2.10" % "3.2.3-play2.3"
    )

resolvers += "bintray" at "http://dl.bintray.com/fmasion/maven"

publishTo := Some("Fred's bintray" at "https://api.bintray.com/maven/fmasion/maven/playHazelCache")

publishMavenStyle := true

lazy val playHazelCache = (project in file(".")).enablePlugins(PlayScala)
