package com.userservice.controller;

import com.userservice.dto.AuthorizationRequest;
import com.userservice.dto.AuthorizationResponse;
import com.userservice.service.AuthorizationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/authorize")
    public AuthorizationResponse authorize(
            @RequestBody AuthorizationRequest request
    ) {
        boolean allowed = authorizationService.isAllowed(
                request.getUserId(),
                request.getMethod(),
                request.getPath()
        );

        return new AuthorizationResponse(allowed);
    }
}
