package com.userservice.controller;

import com.userservice.dto.AssignRoleToUserRequest;
import com.userservice.entity.Role;
import com.userservice.entity.User;
import com.userservice.repository.RoleRepository;
import com.userservice.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/admin/users")
public class UserAdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserAdminController(UserRepository userRepository,
                               RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/assign-role")
    public User assignRoleToUser(
            @RequestBody AssignRoleToUserRequest request
    ) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);
        return userRepository.save(user);
    }
}

