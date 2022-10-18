package net.dg.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
public class BookServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApp.class, args);
    }

}
