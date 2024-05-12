package fax.play.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(Application.class).run(args);
	}
}
