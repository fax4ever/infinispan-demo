package fax.play.client;

import static org.assertj.core.api.Assertions.assertThat;

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
      Play play = cacheService.interactWithTheServer();
      assertThat(play).isNotNull();
   }
}
