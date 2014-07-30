name := "playHazelCache"

version := "3.2.3-play2.2"

libraryDependencies ++= Seq(
    cache,
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.3-play2.2",
    "playhazelcastclient"  % "playhazelcastclient_2.10" % "3.2.3-play2.2"
    )

resolvers += "bintray" at "http://dl.bintray.com/fmasion/maven"

play.Project.playScalaSettings

publishTo := Some("Fred's bintray" at "https://api.bintray.com/maven/fmasion/maven/playHazelCache")

publishMavenStyle := true

