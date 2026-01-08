package com.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        var request = exchange.getRequest();

        System.out.println("➡️  " + request.getMethod() + " " + request.getURI());

        return chain.filter(exchange)
                .doOnSuccess(aVoid ->
                        System.out.println("⬅️  Response sent: " + request.getURI()));
    }
}

