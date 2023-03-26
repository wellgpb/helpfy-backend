package com.example.helpfy.controllers;

import com.example.helpfy.dtos.auth.LoginResponse;
import com.example.helpfy.dtos.user.UserMapper;
import com.example.helpfy.models.User;
import com.example.helpfy.services.auth.AuthService;
import com.example.helpfy.dtos.auth.LoginRequest;
import com.example.helpfy.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    private AuthService authService;
    private UserService userService;
    private UserMapper userMapper;

    public LoginController(AuthService authService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity(authService.auth(loginRequest), HttpStatus.CREATED);
    }

    @GetMapping("/oauth2")
    public LoginResponse oauth(@RequestParam("token") String token) {
        String email = authService.validateToken(token);
        User user = userService.getUserByEmail(email);
        var userResponse = userMapper.fromUserToResponse(user);
        return new LoginResponse(token, userResponse);
    }
}

