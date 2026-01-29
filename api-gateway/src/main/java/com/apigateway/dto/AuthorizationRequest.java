package com.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizationRequest {
    private String userId;
    private String method;
    private String path;
}
