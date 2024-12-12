package com.aos.tp.userService.controller;

import com.aos.tp.userService.model.Users;
import com.aos.tp.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users userRequest) {
        Users newUser = userService.registerUser(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.ok(newUser);  // Return the newly created user (without token)
    }

    // Endpoint to authenticate a user and return a JWT token
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody Users userRequest) {
        String token = userService.authenticateUser(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.ok(token);  // Return the JWT token on successful login
    }
}
