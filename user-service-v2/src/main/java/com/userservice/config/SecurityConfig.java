package com.userservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())     // ❌ disable login page
                .httpBasic(basic -> basic.disable())   // ❌ disable basic auth
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/internal/**").access((authentication, context) -> {
                            HttpServletRequest request = context.getRequest();
                            String internalHeader = request.getHeader("X-Internal-Call");
                            return new AuthorizationDecision("true".equals(internalHeader));
                        })
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
