package com.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class InternalAuthFilter extends OncePerRequestFilter {

    private static final String INTERNAL_AUTH_HEADER = "X-INTERNAL-AUTH";
    private static final String INTERNAL_AUTH_VALUE = "user-service-dev";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(INTERNAL_AUTH_HEADER);

        // allow auth endpoints without header
        if (request.getRequestURI().startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!INTERNAL_AUTH_VALUE.equals(authHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized internal access");
            return;
        }

        filterChain.doFilter(request, response);
    }
}