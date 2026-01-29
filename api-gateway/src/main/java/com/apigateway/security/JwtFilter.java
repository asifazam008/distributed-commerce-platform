package com.apigateway.security;

import com.apigateway.dto.AuthorizationRequest;
import com.apigateway.dto.AuthorizationResponse;
import com.apigateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Component
public class JwtFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WebClient webClient;

    private static final List<String> PUBLIC_URLS = List.of(
            "/auth/",
            "/actuator",
            "/public",
            "/orders/health",
            "/users/health",
            "/users/internal/" // allow calling authorize itself
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        // Skip public endpoints
        if (PUBLIC_URLS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            return unauthorized(exchange);
        }

        String userId = jwtUtil.extractUsername(token);

        // 🔐 CALL USER SERVICE FOR AUTHORIZATION
        return webClient.post()
                .uri("http://user-service/users/internal/authorize")
                .bodyValue(new AuthorizationRequest(userId, method, path))
                .retrieve()
                .bodyToMono(AuthorizationResponse.class)
                .timeout(Duration.ofSeconds(2))
                .retryWhen(Retry.fixedDelay(1, Duration.ofMillis(200)))
                .flatMap(res -> {
                    if (!res.isAllowed()) {
                        return forbidden(exchange);
                    }
                    return chain.filter(
                            exchange.mutate()
                                    .request(r -> r.header("X-Authenticated-User", userId))
                                    .build()
                    );
                })
                .onErrorResume(ex -> {
                    log.error("Authorization service error for path {}: {}", path, ex.getMessage());
                    return forbidden(exchange); // fail-closed
                });

    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}
