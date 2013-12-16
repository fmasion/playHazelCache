import playHazelCache._
import org.specs2.mutable._
import org.specs2.specification.Scope
import play.api.cache.{EhCachePlugin, CacheAPI, CachePlugin}
import play.api.Play



object EhcachePluginComplianceSpec extends ServerIntegrationSpec {

val additionalConfiguration = Map[String,String]()
  
  trait cacheImpls extends Scope with Before {
    
    val key = "ehcachePluginComplianceSpecKey"
      val expiration = 1000

    lazy val ehcache = Play.current.plugin[EhCachePlugin].map(_.api).getOrElse {
      throw new RuntimeException("EhCachePlugin is not found.")
    }
    lazy val hazelcache = Play.current.plugin[PlayHazelCachePlugin].map(_.api).getOrElse {
      throw new RuntimeException("PlayHazelCachePlugin is not found.")
    }

    def testOnValue[T <: Any](value: T) = {
      ehcache.set(key, value, expiration)
      hazelcache.set(key, value, expiration)
      ehcache.get(key) aka ("the value obtained from the Ehcache impl") should be some (value)
      hazelcache.get(key) aka ("the value obtained from the hazelcache impl") should be some (value)
    }

    def both[T](block: CacheAPI => T): T = (ehcache :: hazelcache :: Nil).map(block).last

    def before {
    }
  }

  "Both Ehcache and hazelcache implementations of Cache API" should {

    "returns the same value on setting and then getting a " >> {

      "String value" in new cacheImpls {
        testOnValue("value")
      }

      "Int value" in new cacheImpls {
        testOnValue(123)
      }

      "Long value" in new cacheImpls {
        testOnValue(123L)
      }

      "java.util.Date value" in new cacheImpls {
        testOnValue(new java.util.Date)
      }
    }

    "keep stored data eternally when `expiration` argument is 0" in new cacheImpls {
      both { api =>
        val value = "theValueShouldRemain"
        api.set(key, value, 0)
        api.get(key) must be some (value)
      }
    }

    "remove keys" in new cacheImpls {
      both { api =>
        api.set(key, "value", 0)
        api.get(key) must be some ("value")
        api.remove(key)
        api.get(key) must be none
      }
    }

    "returns None when getting value for empty key" in new cacheImpls {
      both { api =>
        api.get("") must be none
      }
    }
  }

  "Ehcache implementations of Cache API" should {

    "store nulls on setting nulls" in new cacheImpls {

      ehcache.set(key, "aa", 0)
      ehcache.set(key, null, 0)

      ehcache.get(key) must be equalTo (Some(null))

      ehcache.set(key, "aa", 0)
      ehcache.set(key, null, expiration)

      ehcache.get(key) must be equalTo (Some(null))
    }

    "store value for empty key" in new cacheImpls {
      ehcache.set("", "aa", 0)
      ehcache.get("") must be some ("aa")

      ehcache.remove("")
      ehcache.get("") must be none
    }
  }

  "hazelcache implementation of CacheAPI" should {

    "remove keys on setting nulls" in new cacheImpls {

      hazelcache.set(key, "aa", 0)
      hazelcache.set(key, null, expiration)

      hazelcache.get(key) must be none

      hazelcache.set(key, "aa", 0)
      hazelcache.set(key, null, 0)

      hazelcache.get(key) must be none
    }

    "not store value for empty key" in new cacheImpls {
      hazelcache.set("", "aa", 0)
      hazelcache.get("") must be none

      hazelcache.remove("")
      hazelcache.get("") must be none
    }
  }

}
