package com.userservice.dto;

import lombok.Data;

@Data
public class AssignRoleToUserRequest {
    private String userId;
    private Long roleId;
}
