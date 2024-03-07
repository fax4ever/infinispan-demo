package fax.play;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;
import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import fax.play.model.Image;
import fax.play.model.ImageService;
import fax.play.rest.NewImage;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageResourceTest {

   @Inject
   ImageService cache;

   @BeforeAll
   void beforeAll() {
      Image img = new Image("fax", "ciao.jpg", "a cat sitting on the sofa", new Date());
      cache.put(img.key(), img);

      img = new Image("bla", "ciao.jpg", "a dog sitting on the sofa", new Date());
      cache.put(img.key(), img);

      img = new Image("fax", "hello.jpg", "a living room with a white wall and a dog", new Date());
      cache.put(img.key(), img);

      img = new Image("bla", "hello.jpg", "a living room with a white wall and a cat", new Date());
      cache.put(img.key(), img);
   }

   @Test
   void cacheName() {
      given()
            .when()
            .get("/image/cache")
            .then()
            .statusCode(200)
            .body(is("images"));
   }

   @Test
   void addImage() {
      given()
            .contentType(ContentType.JSON)
            .body(new NewImage("fabio", "city.png", "a large building with a clock in the sky"))
            .when()
            .post("/image")
            .then()
            .statusCode(202);
   }

   @Test
   void imagesByUser() {
      List<Image> images = given()
            .when()
            .get("/image/user/fax")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath().getList(".", Image.class);

      assertThat(images).extracting("caption")
            .containsExactlyInAnyOrder("a living room with a white wall and a dog", "a cat sitting on the sofa");
   }

   @Test
   void imagesByCaption() {
      List<Image> images = given()
            .when()
            .get("/image/caption/cat")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath().getList(".", Image.class);

      assertThat(images).extracting("caption")
            .containsExactlyInAnyOrder("a living room with a white wall and a cat", "a cat sitting on the sofa");
   }

   @Test
   void imagesByMoment() {
      String from = "2024-03-03T13:11:22.908+00:00";
      String to = "2124-03-03T13:11:22.908+00:00";

      List<Image> images = given()
            .when()
            .get("/image/from/" + from + "/to/" + to)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath().getList(".", Image.class);

      assertThat(images).isNotEmpty();
   }
}
