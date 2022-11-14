package net.dg.gateway.config;

import net.dg.gateway.filter.ApiKeyFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudGatewayRouting {

	@Bean
	public RouteLocator raitingRoute(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("rating-service", r -> r.path("/rating/**").filters(f -> f.filter(new ApiKeyFilter()))// static
						.uri("lb://RATING-SERVICE"))
				.build();
	}

	@Bean
	public RouteLocator bookRoute(RouteLocatorBuilder builder) {
		return builder.routes().route("book-service", r -> r.path("/book/**").filters(f -> f.filter(new ApiKeyFilter()))// static
				.uri("lb://BOOK-SERVICE")) // static
				.build();
	}

}