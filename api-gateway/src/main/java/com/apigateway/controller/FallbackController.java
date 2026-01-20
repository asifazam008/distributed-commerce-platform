package com.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/fallback/order")
    public Mono<String> orderFallback() {
        return Mono.just("Order service is currently unavailable. Please try later.");
    }
}

