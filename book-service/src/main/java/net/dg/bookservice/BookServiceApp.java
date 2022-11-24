package net.dg.bookservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Book Service API", version = "1.0", description = "Book Service Information"))
public class BookServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApp.class, args);
	}

}
