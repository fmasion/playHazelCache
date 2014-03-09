PlayFramework 2.2.x Hazelcast cache implementation
---------------------------------------

An implementation of CacheAPI for Play 2.2.x that give you the same usage as play's normal cache plugin but that isn't built on top of ehCache but HazelCast.

This cache is cluster wide, distributed and fail-safe (replicated) based on hazelcast distributed hashMap.

## What's the benefit over play's default cache ?

Play uses ehCache internaly if you activate it by adding the `Cache` dependency). [ehCache](http://ehcache.org/) is a great peace of code that ["Go fast and scale"](http://ehcache.org/about/features).

So why then sould I change for this plugin ?
 
 * The main reason is : Because I already use Hazelcast in my architecture. So why would I deploy another piece of code, open more ports on each servers of my cluster for letting an other tool do a subset of what's already provided ?
 
Why Shouldn't I use this plugin ?

* Because adding more stuff in my stack is hype…
* If you have a very big amount of data to store : ehCache stores data off-heap. It allows to store gigabytes without performence and GC problems. This functionality is only available with Hazelcast `commercial version` for now. ;-( 

## Cluster wide cache

Hazelcast is very easy to configure and handles seamlessly new server joining or leaving the cluster without loosing data.
Data are stored in a replicated way and hazelcast uses a local cache for frequently accessed data. 

# How to install it


In your application, add this configuration to the `project/Build.scala` file :


add this resolver (same for both plugins) :

	resolvers += Resolver.url("Fred's GitHub Play Repository", url("http://fmasion.github.com/releases/"))(Resolver.ivyStylePatterns)



add playHazelCache dependency :

	"playhazelcache"  % "playhazelcache_2.10" % "2.6.7"
	
`Choose one of the following hazelcast dependency :`

	"playHazelcast"  % "playHazelcast_2.10" % "2.6.7"
and / or 

	"playHazelcastClient"  % "playHazelcastClient_2.10" % "2.6.7"
	
In your application, add to `conf/play.plugins` (or create the file if it dosn't exist) this configuration :

	600:playHazelCache.PlayHazelCachePlugin
	
`and add respectively to the choosen dependency`	

	500:playHazelcast.api.HazelcastPlugin
and / or
	
	500:playHazelcastClient.api.HazelcastClientPlugin
	
	

The diferences between the 	two is explained in detail in the [playHazelcast plugin documentation](https://github.com/fmasion/playHazelcast)
To make it simple it allows your node to be just a client or a participant to the hazelcast cluster.
The client doesn't own data neither replicat. It only connects to an existing remote hazelcast cluster with all functionality of a node without allocating memory for the shards.

	
	
Last, in `application.conf`, disable the EhCachePlugin - Play's default implementation of CacheAPI:

```
  ehcacheplugin=disabled
```
and at the end add this line :

	include "hazelcast.conf"

create an `hazelcast.conf` file is the `/conf` directory with this content :

	hz.port=25101
	# hz.configfile="conf/config.xml"
	
	# defining group enables multiple hz instances on the same server
	# so the same machine can participate to many cluster
	# Also there are other product that use hazelcast internaly so it provides connection to annother cluster
	# hz.groupname="dev"
	# hz.grouppassword="dev-pass"
	
	# hazelcast tries to connect and increment ports trying to find other machine on the lan
	# it makes easy the use of multiple server on the same machine
	# hz.portautoincrement=true
	
	# No licenceKey is required for community edition
	# hz.licenceKey="XXXXXXXXX"
		
	# for hazelcastClient you configure a list of seeds (some of the member to contact if present)
	# the first seed that respond enables the connection
	# by default addMembershipListener will keep members up to date 
	# so connection to the cluster won't go down if the connected member fails 
	hz.addresses = ["127.0.0.1:5701"]
	
See more detail about playHazelCast configuration [here](https://github.com/fmasion/playHazelcast)
	
Then, you can use the `play.api.cache.Cache` object to store a value in Hazelcast:

```scala
 Cache.set("key", "theValue")
```

This way, memcached tries to retain the stored value eternally.
Of course Memcached does not guarantee eternity of the value, nor can it retain the value on restart.

If you want the value expired after some time:

	Cache.set("key", "theValueWithExpirationTime", 3600)
 	// The value expires after 3600 seconds.

To get the value for a key:

```scala
 val theValue = Cache.getAs[String]("key")
```

You can remove the value (It's not yet a part of Play 2.0's Cache API, though):


 	play.api.Play.current.plugin[MemcachedPlugin].get.api.remove("keyToRemove")
 	

### Disabling the plugin

You can disable the plugin in a similar manner to Play's build-in Ehcache Plugin.
To disable the plugin in `application.conf`:

```
  playhazelcacheplugin=disabled
```

### Configure logging

```
  logger.playHazelCache=DEBUG
```

### HazelCast collection Name

You can define the collection name in which to put/get/remove keys/values. It defaults to `playhazelcache`
You can overide it, for exemple, if you use an hazelcast cluster for more than one app so each app will be isolated preventing from key name conflict.
You can also override it to a value you're sure your hazelcast app will never use. 

```
  playhazelcache.collectionname=playhazelcache
```

## Sample application

You can find a working configuration in the `/samples/scala` folder. This application and test cases are greatly ""inspired"" from [play2-memcached](https://github.com/mumoshu/play2-memcached) 

### Acknowledgement

Thanks to [KUOKA Yusuke](https://github.com/mumoshu) for the base of this code comming from [play2-memcached](https://github.com/mumoshu/play2-memcached).

## Licence

This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 F. Masion.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

blah blah blaahhh
