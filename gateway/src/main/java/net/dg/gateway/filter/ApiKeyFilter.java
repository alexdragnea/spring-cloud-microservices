package net.dg.gateway.filter;

import net.dg.gateway.constants.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class ApiKeyFilter implements GatewayFilter, Ordered {

	private static final Logger log = LoggerFactory.getLogger(ApiKeyFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		String routeId = route.getId();
		List<String> apiKeyHeader = exchange.getRequest().getHeaders().get(AppConstant.API_KEY);
		log.info("Api key: {}", apiKeyHeader);
		if (CollectionUtils.isEmpty(apiKeyHeader) || !checkApikey(routeId, apiKeyHeader.get(0))) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					"You cannot consume this service. Please check your api key.");
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private boolean checkApikey(String routeId, String apikey) {

		return apikey.equals(AppConstant.API_KEY_VALUE);
	}

}
