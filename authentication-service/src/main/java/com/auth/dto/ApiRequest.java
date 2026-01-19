package com.auth.dto;

import lombok.Data;

@Data
public class ApiRequest<T> {

    private String requestId;

    private T data;
}