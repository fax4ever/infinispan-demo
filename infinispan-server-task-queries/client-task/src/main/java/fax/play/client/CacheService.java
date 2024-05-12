package fax.play.client;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fax.play.model.Play;

@Service
public class CacheService {

   @Autowired
   private RemoteCacheManager cacheManager;

   public Play interactWithTheServer() {
      RemoteCache<Integer, Play> cache = cacheManager.getCache(InfinispanConfiguration.CACHE_NAME);
      cache.clear();

      Play monopoly = new Play("Monopoly", "Monopoly is a multiplayer economics-themed board game.", 7);
      cache.put(1, monopoly);

      return cache.get(1);
   }
}
