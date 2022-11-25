package net.dg.ratingservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Rating Service API", version = "1.0", description = "Rating Information"))
public class RatingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}

}
