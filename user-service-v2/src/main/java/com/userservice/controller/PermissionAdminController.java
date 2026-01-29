package com.userservice.controller;

import com.userservice.entity.Permission;
import com.userservice.repository.PermissionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/admin/permissions")
public class PermissionAdminController {

    private final PermissionRepository permissionRepository;

    public PermissionAdminController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @PostMapping
    public Permission create(@RequestBody Permission permission) {
        return permissionRepository.save(permission);
    }
}
