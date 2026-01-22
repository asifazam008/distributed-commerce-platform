package com.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInternalResponse {
    private String userId;
    private String status;
    private List<String> roles;
    private List<String> permissions;
}
