package com.example.helpfy.services.user;

import com.example.helpfy.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getUserById(Long id);

    User saveUser(User user);
    User updateUser(Long id, User newUser);
    void deleteUser(Long id);
}
