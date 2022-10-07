package net.dg.raitingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RaitingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RaitingServiceApplication.class, args);
    }

}
