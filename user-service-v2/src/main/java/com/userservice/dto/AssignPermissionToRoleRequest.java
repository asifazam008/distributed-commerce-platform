package com.userservice.dto;

import lombok.Data;

@Data
public class AssignPermissionToRoleRequest {
    private Long roleId;
    private Long permissionId;
}
