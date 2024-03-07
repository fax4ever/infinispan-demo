package fax.play;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.configuration.StringConfiguration;
import org.infinispan.commons.marshall.ProtoStreamMarshaller;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import fax.play.model.Image;
import fax.play.model.Image.ImageSchema;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
@Startup
public class Config implements Closeable {

   public static final String CACHE_NAME = "images";

   @ConfigProperty(name = "quarkus.infinispan.host")
   String host;

   private RemoteCacheManager cacheManager;

   private RemoteCache<String, Image> cache;

   @PostConstruct
   public void init() {
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.addServer().host(host).port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
            .security()
            .authentication()
            .username("admin")
            .password("password")
            .marshaller(ProtoStreamMarshaller.class)
            // handle the schema client side
            .addContextInitializer(ImageSchema.INSTANCE);

      cacheManager = new RemoteCacheManager(builder.build());

      cacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME)
            // handle the schema server side
            .put(ImageSchema.INSTANCE.getProtoFileName(), ImageSchema.INSTANCE.getProtoFile());
      cache = createCache();
   }

   private RemoteCache<String, Image> createCache() {
      String configuration;
      try (InputStream resourceAsStream = Config.class.getClassLoader().getResourceAsStream("cache.yaml")) {
         configuration = new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      return cacheManager.administration().getOrCreateCache(CACHE_NAME, new StringConfiguration(configuration));
   }

   @Produces
   public RemoteCache<String, Image> cache() {
      return cache;
   }

   @Override
   public void close() {
      cacheManager.close();
   }
}
