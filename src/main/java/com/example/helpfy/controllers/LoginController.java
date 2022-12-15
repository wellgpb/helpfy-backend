package com.example.helpfy.controllers;

import com.example.helpfy.services.auth.AuthService;
import com.example.helpfy.dtos.auth.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController()
@RequestMapping("/login")
public class LoginController {
    private AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity(authService.auth(loginRequest), HttpStatus.CREATED);
    }
}

