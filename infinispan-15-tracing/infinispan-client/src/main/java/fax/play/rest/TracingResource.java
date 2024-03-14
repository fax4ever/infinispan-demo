package fax.play.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.api.CacheContainerAdmin;

import fax.play.model.Image;
import io.quarkus.infinispan.client.Remote;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("tracing")
public class TracingResource {

   private static final String CONTAINER = "container";
   private static final String CLUSTER = "cluster";
   private static final String PERSISTENCE = "persistence";
   private static final String X_SITE = "x-site";

   @Inject
   @Remote("images")
   RemoteCache<String, Image> cache;

   @Inject
   RemoteCacheManager cacheManager;

   private Set<String> enabledCategories;

   @PostConstruct
   public void init() {
      enabledCategories = new LinkedHashSet<>();
      enabledCategories.add(CONTAINER);
      enabledCategories.add(X_SITE);
   }

   @POST
   public String enable() {
      cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
            .updateConfigurationAttribute(cache.getName(), "tracing.enabled", "true");
      return "enabled";
   }

   @DELETE
   public String disable() {
      cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
            .updateConfigurationAttribute(cache.getName(), "tracing.enabled", "false");
      return "disabled";
   }

   @POST
   @Path(CONTAINER)
   public String container() {
      return enable(CONTAINER);
   }

   @POST
   @Path(CLUSTER)
   public String cluster() {
      return enable(CLUSTER);
   }

   @POST
   @Path(PERSISTENCE)
   public String persistence() {
      return enable(PERSISTENCE);
   }

   @POST
   @Path(X_SITE)
   public String xSite() {
      return enable(X_SITE);
   }

   @DELETE
   @Path(CONTAINER)
   public String containerOff() {
      return disable(CONTAINER);
   }

   @DELETE
   @Path(CLUSTER)
   public String clusterOff() {
      return disable(CLUSTER);
   }

   @DELETE
   @Path(PERSISTENCE)
   public String persistenceOff() {
      return disable(PERSISTENCE);
   }

   @DELETE
   @Path(X_SITE)
   public String xSiteOff() {
      return disable(X_SITE);
   }

   private String enable(String category) {
      enabledCategories.add(category);
      updateConfig();
      return "enabled categories: " + enabledCategories;
   }

   private String disable(String category) {
      enabledCategories.remove(category);
      updateConfig();
      return "enabled categories: " + enabledCategories;
   }

   private void updateConfig() {
      String config = (enabledCategories.isEmpty()) ? "" : String.join(",", enabledCategories);
      cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
            .updateConfigurationAttribute(cache.getName(), "tracing.categories", config);
   }
}
