package com.aos.tp.userService.service;

import com.aos.tp.userService.JwtTokenProvider;
import com.aos.tp.userService.model.Users;
import com.aos.tp.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Register a user
    public Users registerUser(String username, String password) {
        // Check if username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(password);

        // Create and save the new user
        Users newUser = new Users(username, encodedPassword);
        return userRepository.save(newUser);
    }

    // Authenticate a user and generate JWT token
    public String authenticateUser(String username, String password) {
        // Find the user by username
        Optional<Users> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid username or password!");
        }

        Users user = optionalUser.get();

        // Check if the password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        // Generate JWT token
        return jwtTokenProvider.generateToken(username);
    }
}
