package com.userservice.controller;

import com.userservice.dto.AssignPermissionToRoleRequest;
import com.userservice.entity.Permission;
import com.userservice.entity.Role;
import com.userservice.repository.PermissionRepository;
import com.userservice.repository.RoleRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/admin/roles")
public class RoleAdminController {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleAdminController(RoleRepository roleRepository,
                               PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @PostMapping("/assign-permission")
    public Role assignPermissionToRole(
            @RequestBody AssignPermissionToRoleRequest request
    ) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(request.getPermissionId())
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        role.getPermissions().add(permission);
        return roleRepository.save(role);
    }
}

