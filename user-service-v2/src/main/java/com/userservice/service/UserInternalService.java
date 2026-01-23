package com.userservice.service;

import com.userservice.dto.UserInternalResponse;
import com.userservice.entity.Permission;
import com.userservice.entity.User;
import com.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInternalService {

    private final UserRepository userRepository;

    public UserInternalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInternalResponse getUserDetails(String userId) {

        User user = userRepository.findWithRolesAndPermissions(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getRoles()
                .stream()
                .map(r -> r.getName())
                .distinct()
                .toList();

        List<String> permissions = user.getRoles()
                .stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();

        return UserInternalResponse.builder()
                .userId(user.getUserId())
                .status(user.getStatus())
                .roles(roles)
                .permissions(permissions)
                .build();
    }
}
