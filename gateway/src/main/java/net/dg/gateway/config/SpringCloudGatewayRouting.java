package net.dg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudGatewayRouting {

    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("paymentId", r->r.path("/raiting/**").uri("lb://RAITING-SERVICE")) //dynamic routing
                .route("orderId", r->r.path("/book/**").uri("lb://BOOK-SERVICE")) //dynamic routing
                .build();
    }
}