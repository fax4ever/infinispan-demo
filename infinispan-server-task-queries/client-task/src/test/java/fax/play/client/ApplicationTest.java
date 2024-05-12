package fax.play.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.infinispan.client.hotrod.RemoteCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fax.play.model.Play;

@SpringBootTest
public class ApplicationTest {

   @Autowired
   private CacheService cacheService;

   @Test
   public void test() {
      Integer size = cacheService.interactWithTheServer();
      assertThat(size).isEqualTo(2);
      RemoteCache<Integer, Play> cache = cacheService.getCache();
      assertThat(cache.size()).isEqualTo(3);
   }
}
