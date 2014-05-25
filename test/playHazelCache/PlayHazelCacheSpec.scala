package playHazelCache

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import play.api.mvc.{Action, Results}
import play.api.test.{FakeApplication, FakeRequest}
//import play.api.test.FakeRequest
import play.api.test.Helpers._
//GET
//import play.api.test.Helpers.contentAsString
//import play.api.test.Helpers.defaultAwaitTimeout
//import play.api.test.Helpers.route
//import play.api.test.Helpers.running
//import play.api.test.Helpers.status
//import play.api.test.Helpers.writeableOf_AnyContentAsEmpty
import play.api.cache.Cache
import play.api.Play.current

import org.specs2.runner.JUnitRunner


@RunWith(classOf[JUnitRunner])
class PlayHazelCacheSpec extends Specification {

  val key = "HazelCachSpecKey"
  val value = "value"
  val expiration = 1000

  lazy val hazelCacheApp = FakeApplication(
    additionalPlugins = Seq("playHazelcast.api.HazelcastPlugin",
      "playHazelCache.PlayHazelCachePlugin"),
    withRoutes = {
      case ("GET", "/cache/get") =>
        Action(
          Results.Ok(
            play.api.Play.current.plugin(classOf[PlayHazelCachePlugin]).get.api.get("key").toString).as("text"))
      case ("GET", "/cache/set") =>
        play.api.Play.current.plugin(classOf[PlayHazelCachePlugin]).get.api.set("key", "Bella Lou", 0).toString
        Action(Results.Ok("Cached.").as("text"))
    })

  sequential

  def connectingLocalHazelcast[T](block: => T): T =
    running(
      hazelCacheApp.copy(
        additionalConfiguration = Map(
          "ehcacheplugin" -> "disabled",
          "playhazelcache.collectionname" -> "playhazelcache",
          "hz.port" -> 25101,
          "hz.addresses" -> """["127.0.0.1:5701"]"""))) {
        play.api.Play.current.plugin(classOf[PlayHazelCachePlugin]).get.api.remove("key")
        block
      }

  def c(url: String): String = {
    contentAsString(route(FakeRequest(GET, url)).get)
  }

  "The scala sample application" should {

    "return a cached data" in {
      connectingLocalHazelcast {
        c("/cache/get") must equalTo("None")
        c("/cache/set") // must equalTo("Cached.")
        c("/cache/get") must equalTo("Some(Bella Lou)")
      }
    }

  }
  
  "play.api.cache.Cache" should {

    "remove keys on setting nulls" in connectingLocalHazelcast {
      Cache.set(key, value, expiration)
      Cache.get(key) must be some (value)
      Cache.set(key, null)
      Cache.get(key) must be none
    }
  }
}