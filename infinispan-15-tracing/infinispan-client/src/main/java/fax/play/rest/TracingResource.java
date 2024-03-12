package fax.play.rest;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.api.CacheContainerAdmin;

import fax.play.model.Image;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("tracing")
public class TracingResource {

   @Inject
   RemoteCache<String, Image> cache;

   @Inject
   RemoteCacheManager cacheManager;

   private boolean container = true;
   private boolean cluster = true;
   private boolean persistence = true;

   @GET
   @Path("container")
   public String container() {
      container = !container;
      updateConfig();
      return "container => " + container;
   }

   @GET
   @Path("cluster")
   public String cluster() {
      cluster = !cluster;
      updateConfig();
      return "cluster => " + cluster;
   }

   @GET
   @Path("persistence")
   public String persistence() {
      persistence = !persistence;
      updateConfig();
      return "persistence => " + persistence;
   }

   private void updateConfig() {
      cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
            .updateConfigurationAttribute(cache.getName(), "tracing.categories", "cluster, container");
   }
}
