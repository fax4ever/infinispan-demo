package fax.play.client;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestEntrypoint {

   @Autowired
   private RemoteCacheManager cacheManager;

   @GetMapping("/ciao")
   public String ciao() {
      RemoteCache<String, String> cache = cacheManager.getCache(InfinispanConfiguration.CACHE_NAME);
      return cache.get("ciao");
   }

}
