import playHazelCache._
import java.io.{PrintWriter, BufferedReader, InputStreamReader}
import java.net.Socket
import org.specs2.mutable._
import org.specs2.execute.{AsResult, Result}
import org.specs2.specification.Scope
import play.api.cache.Cache
import play.api.Play.current
import play.api.test.{FakeApplication, TestServer}
import play.api.test.Helpers._

object MemcachedIntegrationSpec extends Specification {

  sequential
  
  val additionalConfiguration = Map(
    "ehcacheplugin" -> "disabled"
  )

  case class context(additionalConfiguration: Map[String, String] = additionalConfiguration) extends Around {

    val key = "IntegrationSpecKey"
    val value = "value"
    val expiration = 1000

    def around[T](t: => T)(implicit a: AsResult[T]) = a.asResult {
      running(TestServer(3333, FakeApplication(additionalConfiguration = additionalConfiguration))) {
        t
      }
    }

  }


  "play.api.cache.Cache" should {

    "remove keys on setting nulls" in new context {

      Cache.set(key, value, expiration)
      Cache.get(key) must be some (value)

      Cache.set(key, null)
      Cache.get(key) must be none
    }
  }

  "The CacheAPI implementation of MemcachedPlugin" should {

    "remove keys on setting nulls" in new context {

      val api = current.plugin[PlayHazelCachePlugin].map(_.api).get

      api.set(key, null, expiration)
      api.get(key) must be none

    }

    "store the data when setting expiration time to zero (maybe eternally)" in new context {

      val api = current.plugin[PlayHazelCachePlugin].map(_.api).get

      api.set(key, value + "*", expiration)
      api.set(key, value, 0)
      api.get(key) must be some (value)
    }

    "provides its own way to remove stored values" in new context {

      val api = current.plugin[PlayHazelCachePlugin].get.api

      api.set(key, value, expiration)
      api.get(key) should be some(value)
      api.remove(key)
      api.get(key) should be none

    }

    "has an another way to remove the stored value" in new context {

      Cache.set(key, "foo")
 
      current.plugin[PlayHazelCachePlugin].foreach(_.api.remove(key))

      Cache.get(key) must beEqualTo (None)
    }

  }

}
