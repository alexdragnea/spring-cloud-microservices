package net.dg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudGatewayRouting {

    @Bean
    public RouteLocator raitingRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("rating-service", r -> r.path("/rating/**").uri("lb://RATING-SERVICE")) //static routing
                .build();
    }

    @Bean
    public RouteLocator bookRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("book-service", r -> r.path("/book/**").uri("lb://BOOK-SERVICE")) //static routing
                .build();
    }
}