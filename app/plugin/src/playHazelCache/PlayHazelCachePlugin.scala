package playHazelCache

import play.api.cache.{ CacheAPI, CachePlugin }
import play.api.{ Logger, Play, Application }
import playHazelcastClient.api.HazelcastClientPlugin
import com.hazelcast.core.HazelcastInstance
import playHazelcastClient.api.PlayHzClient
import playHazelcast.api.HazelcastPlugin
import playHazelcast.api.PlayHz

class PlayHazelCachePlugin(app: Application) extends CachePlugin {

  lazy val logger = Logger("playHazelCache.PlayHazelCastPlugin")
  lazy val collectionname: String = app.configuration.getString("playhazelcache.collectionname").getOrElse("playhazelcache")

  lazy val hzClient: Option[HazelcastInstance] = play.api.Play.current.plugin[HazelcastClientPlugin].flatMap(p => PlayHzClient.getClient)
  lazy val hzInstance: Option[HazelcastInstance] = play.api.Play.current.plugin[HazelcastPlugin].flatMap(p => PlayHz.getInstance)
  lazy val client = hzClient.orElse(hzInstance).get

  lazy val api = new CacheAPI {

    def get(key: String) = {
      if (key.isEmpty) {
        None
      } else {
        logger.debug("Getting the cached for key " + key)
        Option(client.getMap[String, Any](collectionname).get(key))
      }
    }

    def set(key: String, value: Any, expiration: Int) {
            if (!key.isEmpty) {
              client.getMap[String, Any](collectionname).set(key, value, expiration, java.util.concurrent.TimeUnit.SECONDS)
            }
    }

    def remove(key: String) {
      if (!key.isEmpty) {
        client.getMap[String, Any](collectionname).remove(key)
      }
    }
  }

  /**
   * Is this plugin enabled.
   *
   */
  override lazy val enabled = {
    !app.configuration.getString("playhazelcacheplugin").filter(_ == "disabled").isDefined
  }

  override def onStart() {
    logger.info("Starting PlayHazelCachePlugin.")
  }

  override def onStop() {
    logger.info("Stopping PlayHazelCachePlugin.")
  }
}
