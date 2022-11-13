package com.example.helpfy.dtos.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String avatarLink;
}
