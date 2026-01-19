package com.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private String requestId;

    private String status;

    private String message;

    private T data;
}