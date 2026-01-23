package com.userservice.dto;

import lombok.Data;

@Data
public class AuthorizationRequest {

    private String userId;   // JWT sub
    private String method;   // GET, POST, PUT, DELETE
    private String path;     // /orders/123/cancel

    // getters & setters
}