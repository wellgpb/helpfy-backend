package com.example.helpfy.dtos.user;

import com.example.helpfy.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse fromUserToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .lastName(user.getLastName())
                .avatarLink(user.getAvatarLink())
                .build();
    }

    public User fromRequestToUser(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .lastName(userRequest.getLastName())
                .avatarLink(userRequest.getAvatarLink())
                .build();
    }

    public User toUserPUT(UserRequestPUT userRequestPUT) {
        return User.builder()
                .email(userRequestPUT.getEmail())
                .name(userRequestPUT.getName())
                .lastName(userRequestPUT.getLastName())
                .avatarLink(userRequestPUT.getAvatarLink())
                .build();
    }
}
