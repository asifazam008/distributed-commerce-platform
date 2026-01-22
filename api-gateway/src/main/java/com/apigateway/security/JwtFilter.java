package com.apigateway.security;

import com.apigateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/auth/login",
            "/auth/register",
            "/auth/refresh-token",
            "/actuator",
            "/public",
            "/orders/health",
            "/users/health"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (PUBLIC_URLS.contains(path)) {
            return chain.filter(exchange);
        }
        log.debug("JWT Filter invoked for path: {}", path);

        // Skip public endpoints
        if (PUBLIC_URLS.stream().anyMatch(path::startsWith)) {
            log.debug("Path [{}] is excluded from JWT validation", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Unauthorized request to [{}] – Missing or invalid Authorization header", path);
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            log.warn("Unauthorized request to [{}] – Invalid or expired JWT token", path);
            return unauthorized(exchange);
        }

        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        log.info("Authenticated request – user: {}, role: {}, path: {}", username, role, path);

        // RBAC enforcement
        boolean allowed = RbacConfig.isAccessAllowed(path, role);

        if (!allowed) {
            log.warn("Access denied for user [{}] with role [{}] on path [{}]", username, role, path);
            return forbidden(exchange);
        }

        log.debug("RBAC allowed for user [{}] on path [{}]", username, path);

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(r -> r
                        .header("X-Authenticated-User", username)
                        .header("X-User-Role", role)
                )
                .build();

        return chain.filter(mutatedExchange);
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
