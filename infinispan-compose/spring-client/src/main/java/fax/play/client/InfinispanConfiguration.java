package fax.play.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.infinispan.commons.marshall.ProtoStreamMarshaller;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class InfinispanConfiguration {

   public static final String CACHE_NAME = "play";

   @Bean
   @Order(Ordered.HIGHEST_PRECEDENCE)
   public InfinispanRemoteCacheCustomizer caches() {
      return b -> {
         URI cacheConfigUri;
         try {
            cacheConfigUri = this.getClass().getClassLoader().getResource("play.xml").toURI();
         } catch (URISyntaxException e) {
            throw new RuntimeException(e);
         }

         b.remoteCache(CACHE_NAME).configurationURI(cacheConfigUri);
         b.remoteCache(CACHE_NAME).marshaller(ProtoStreamMarshaller.class);
      };
   }
}
