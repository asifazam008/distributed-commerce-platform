package com.userservice.controller;

import com.userservice.dto.*;
import com.userservice.service.UserInternalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
public class UserInternalController {

    private final UserInternalService userService;

    public UserInternalController(UserInternalService userService) {
        this.userService = userService;
    }

    @PostMapping("/details")
    public ApiResponse<UserInternalResponse> getUserDetails(
            @RequestBody ApiRequest<UserInternalRequest> request
    ) {
        UserInternalResponse data =
                userService.getUserDetails(request.getData().getUserId());

        return ApiResponse.<UserInternalResponse>builder()
                .requestId(request.getRequestId())
                .status("SUCCESS")
                .message("User internal details fetched")
                .data(data)
                .build();
    }
}
