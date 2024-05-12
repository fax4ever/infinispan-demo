package fax.play.client;

import java.util.Collections;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fax.play.model.Play;

@Service
public class CacheService {

   @Autowired
   private RemoteCacheManager cacheManager;

   public Integer interactWithTheServer() {
      RemoteCache<Integer, Play> cache = getCache();
      cache.clear();

      Play monopoly = new Play("Monopoly", "Monopoly is a multiplayer economics-themed board game.", 7);
      cache.put(1, monopoly);

      // Run the server task.
      return cache.execute("play-task", Collections.emptyMap());
   }

   public RemoteCache<Integer, Play> getCache() {
      return cacheManager.getCache(InfinispanConfiguration.CACHE_NAME);
   }
}
