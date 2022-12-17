package com.example.helpfy.controllers;

import com.example.helpfy.dtos.user.UserMapper;
import com.example.helpfy.dtos.user.UserRequest;
import com.example.helpfy.dtos.user.UserRequestPUT;
import com.example.helpfy.dtos.user.UserResponse;
import com.example.helpfy.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return new ResponseEntity<>(userMapper.fromUserToResponse(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid UserRequest user) {
        var userSaved = userService.saveUser(userMapper.fromRequestToUser(user));
        var userResponse = userMapper.fromUserToResponse(userSaved);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestPUT userRequest) {
        var user = userMapper.toUserPUT(userRequest);
        var updatedUser = userService.updateUser(id, user);
        var userResponse = userMapper.fromUserToResponse(updatedUser);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
