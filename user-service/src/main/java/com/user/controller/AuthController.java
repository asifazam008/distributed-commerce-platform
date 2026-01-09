package com.user.controller;

<<<<<<< HEAD

import com.user.dto.RegisterRequest;
=======
import com.user.dto.RegisterRequest;
import com.user.services.UserService;
>>>>>>> 8e7ecc5cb59e58f63523d304fecd58d5a6af6b9c
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import com.user.services.UserService;
=======
>>>>>>> 8e7ecc5cb59e58f63523d304fecd58d5a6af6b9c

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
