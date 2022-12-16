package com.example.helpfy.dtos.auth;

import com.example.helpfy.dtos.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserResponse user;
}
