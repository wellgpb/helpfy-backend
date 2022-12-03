package com.example.helpfy.services.user;

import com.example.helpfy.models.User;

public interface UserService {
    User getUserById(Long id);

    User saveUser(User user);
    User updateUser(Long id, User newUser);
    void deleteUser(Long id);
}
