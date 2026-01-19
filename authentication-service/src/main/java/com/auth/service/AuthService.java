package com.auth.service;

import com.auth.dto.*;
import com.auth.entity.User;
import com.auth.exception.UserAlreadyExistsException;
import com.auth.repository.UserRepository;
import com.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponse registerUser(UserRegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .phoneNumber(savedUser.getPhoneNumber())
                .role(savedUser.getRole())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }


    public ApiResponse<LoginResponse> loginUser(ApiRequest<LoginRequest> request) {

        LoginRequest loginRequest = request.getData();

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            return ApiResponse.<LoginResponse>builder()
                    .requestId(request.getRequestId())
                    .status("FAILURE")
                    .message("Invalid username or password")
                    .data(null)
                    .build();
        }

        User user = userOptional.get();

        boolean passwordMatches = passwordEncoder.matches(
                loginRequest.getPassword(), user.getPassword());

        if (!passwordMatches) {
            return ApiResponse.<LoginResponse>builder()
                    .requestId(request.getRequestId())
                    .status("FAILURE")
                    .message("Invalid username or password")
                    .data(null)
                    .build();
        }

        // Generate real JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();

        return ApiResponse.<LoginResponse>builder()
                .requestId(request.getRequestId())
                .status("SUCCESS")
                .message("Login successful")
                .data(loginResponse)
                .build();

    }

    public ApiResponse<LoginResponse> refreshToken(String authHeader, String requestId) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ApiResponse.<LoginResponse>builder()
                    .status("FAILURE")
                    .message("Invalid Authorization header")
                    .data(null)
                    .build();
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);

            if (!jwtUtil.validateToken(token, username)) {
                throw new RuntimeException("Invalid token");
            }

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newToken = jwtUtil.generateToken(user.getUsername(), user.getRole());

            LoginResponse response = LoginResponse.builder()
                    .token(newToken)
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();

            return ApiResponse.<LoginResponse>builder()
                    .requestId(requestId)
                    .status("SUCCESS")
                    .message("Token refreshed successfully")
                    .data(response)
                    .build();

        } catch (Exception e) {
            return ApiResponse.<LoginResponse>builder()
                    .requestId(requestId)
                    .status("FAILURE")
                    .message("Invalid or expired token")
                    .data(null)
                    .build();
        }
    }
}

