package com.auth.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
