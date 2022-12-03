package com.example.helpfy.dtos.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestPUT {
    private String email;
    private String name;
    private String lastName;
    private String avatarLink;
}
