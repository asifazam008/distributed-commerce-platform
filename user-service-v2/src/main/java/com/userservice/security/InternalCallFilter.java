package com.userservice.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class InternalCallFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getRequestURI().startsWith("/internal/")) {
            String internalHeader = req.getHeader("X-Internal-Call");

            if (!"true".equals(internalHeader)) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("Forbidden: Internal API");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
