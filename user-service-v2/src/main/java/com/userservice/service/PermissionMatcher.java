package com.userservice.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class PermissionMatcher {

    // Central policy map (can later move to DB)
    private static final Map<String, String> POLICY_MAP = Map.of(
            "POST:/orders", "ORDER_CREATE",
            "GET:/orders", "ORDER_READ",
            "GET:/orders/*", "ORDER_READ",
            "PUT:/users/me", "USER_PROFILE_UPDATE"
    );

    public boolean isAllowed(Set<String> userPermissions,
                             String method,
                             String path) {

        String requiredPermission = resolvePermission(method, path);
        if (requiredPermission == null) {
            return false; // default deny
        }

        return userPermissions.contains(requiredPermission);
    }

    private String resolvePermission(String method, String path) {
        String exactKey = method + ":" + path;
        if (POLICY_MAP.containsKey(exactKey)) {
            return POLICY_MAP.get(exactKey);
        }

        // simple wildcard support
        return POLICY_MAP.entrySet().stream()
                .filter(e -> e.getKey().endsWith("*"))
                .filter(e -> path.startsWith(e.getKey().split(":")[1].replace("*", "")))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}
