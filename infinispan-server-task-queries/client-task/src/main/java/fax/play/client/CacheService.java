package fax.play.client;

import java.util.HashMap;
import java.util.Map;

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

      Map<String, String> parameters = new HashMap<>();
      parameters.put("name", "developer");

      // Run the server task.
      String greet = cache.execute("hello-task", parameters);

      return cache.get(1);
   }
}
