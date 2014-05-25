name := "playHazelCache"

version := "3.2.1"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += Resolver.file("Local repo", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
    cache,
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.1",
    "playhazelcastclient"  % "playhazelcastclient_2.10" % "3.2.1"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalacOptions ++= Seq("-deprecation","-feature")
