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

    // Point de terminaison pour enregistrer un utilisateur
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users userRequest) {
        Users newUser = userService.registerUser(userRequest.getUsername(), userRequest.getPassword());
        return ResponseEntity.ok(newUser);
    }

    // Point de terminaison pour authentifier un utilisateur
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody Users userRequest) {
        String token = String.valueOf(userService.authenticateUser(userRequest.getUsername(), userRequest.getPassword()));
        return ResponseEntity.ok(token);
    }
}
