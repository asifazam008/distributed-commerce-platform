package com.auth.controller;

import com.auth.dto.*;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import com.auth.service.AuthService;
import com.auth.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody ApiRequest<UserRegisterRequest> request) {

        UserResponse response = authService.registerUser(request.getData());

        return ApiResponse.<UserResponse>builder()
                .requestId(request.getRequestId())
                .status("SUCCESS")
                .message("User registered successfully")
                .data(response)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody ApiRequest<LoginRequest> request) {
        // Login logic to be implemented
        return authService.loginUser(request);
    }

    @PostMapping("/refresh-token")
    public ApiResponse<LoginResponse> refreshToken(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ApiRequest<Void> request) {

        return authService.refreshToken(authHeader, request.getRequestId());
    }

}
