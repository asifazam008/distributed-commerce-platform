package com.userservice.dto;

public class AuthorizationResponse {

    private boolean allowed;

    public AuthorizationResponse(boolean authorized) {
        this.allowed = allowed;
    }

    public boolean isAllowed() {
        return allowed;
    }
}
