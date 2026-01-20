package com.apigateway.security;

import java.util.*;

public class RbacConfig {

    public static final Map<String, List<String>> ROLE_RULES = new HashMap<>();

    static {
        // USER permissions
        ROLE_RULES.put("/orders/create", List.of("USER", "ADMIN"));
        ROLE_RULES.put("/orders/find", List.of("USER", "ADMIN"));
        ROLE_RULES.put("/orders/findByCustomer", List.of("USER", "ADMIN"));
        ROLE_RULES.put("/orders/total", List.of("USER", "ADMIN"));

        // ADMIN only permissions
        ROLE_RULES.put("/orders/findAll", List.of("ADMIN"));
        ROLE_RULES.put("/orders/findByStatus", List.of("ADMIN"));

        // Pattern based rules
        ROLE_RULES.put("/orders/.*/cancel", List.of("ADMIN"));
        ROLE_RULES.put("/orders/.*/items", List.of("USER", "ADMIN"));
    }

    public static boolean isAccessAllowed(String path, String role) {

        for (Map.Entry<String, List<String>> entry : ROLE_RULES.entrySet()) {

            String pattern = entry.getKey();

            if (path.matches(pattern.replace("**", ".*"))) {
                return entry.getValue().contains(role);
            }
        }

        // If no rule matched → deny by default
        return false;
    }
}
