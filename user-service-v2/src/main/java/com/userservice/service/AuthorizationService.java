package com.userservice.service;

import com.userservice.entity.Permission;
import com.userservice.entity.User;
import com.userservice.entity.UserStatus;
import com.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authorization decision point (PDP)
     */
    public boolean isAllowed(String userId, String method, String path) {

        User user = userRepository.findWithRolesAndPermissions(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // 1️⃣ Block non-active users
        if (user.getStatus() != UserStatus.ACTIVE) {
            return false;
        }

        // 2️⃣ RBAC evaluation
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> matches(permission, method, path));
    }

    /**
     * Permission match logic
     */
    private boolean matches(Permission permission, String method, String path) {

        // HTTP method must match
        if (!permission.getMethod().equalsIgnoreCase(method)) {
            return false;
        }

        // Path must match pattern (/orders/** etc.)
        return pathMatcher.match(permission.getPathPattern(), path);
    }
}
