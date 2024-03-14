package fax.play;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TracingResourceTest {

   @Test
   public void smokeTest() {
      // http POST http://localhost:8080/tracing/persistence
      given()
            .when()
            .post("/tracing/persistence")
            .then()
            .statusCode(200)
            .body(is("enabled categories: [container, x-site, persistence]"));

      // http POST http://localhost:8080/tracing/cluster
      given()
            .when()
            .post("/tracing/cluster")
            .then()
            .statusCode(200)
            .body(is("enabled categories: [container, x-site, persistence, cluster]"));

      // http DELETE http://localhost:8080/tracing
      given()
            .when()
            .delete("/tracing")
            .then()
            .statusCode(200)
            .body(is("disabled"));
   }

}
