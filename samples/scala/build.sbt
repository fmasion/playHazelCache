name := "playHazelCache-scala-sample"

//organization := "com.intelligent-es"

version := "3.2.1"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += Resolver.file("Local repo", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
    "playhazelcast"  % "playhazelcast_2.10" % "3.2.1",
    "playhazelcache"  % "playhazelcache_2.10" % "3.2.1",
    cache
)     

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalacOptions ++= Seq("-deprecation","-feature")
