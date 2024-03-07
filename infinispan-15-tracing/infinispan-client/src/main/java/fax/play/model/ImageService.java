package fax.play.model;

import org.infinispan.query.dsl.QueryFactory;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Startup
public class ImageService {

   public void put(String key, Image img) {

   }

   public String getName() {
      return null;
   }

   public QueryFactory queryFactory() {
      return null;
   }
}
