package com.aos.tp.userService.service;

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

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Méthode pour enregistrer un utilisateur
    public Users registerUser(String username, String password) {
        // Vérifie si le username existe déjà
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Le nom d'utilisateur existe déjà !");
        }

        // Encode le mot de passe
        String encodedPassword = passwordEncoder.encode(password);

        // Crée et sauvegarde l'utilisateur
        Users newUser = new Users(username, encodedPassword);
        return userRepository.save(newUser);
    }

    // Méthode pour authentifier un utilisateur (sans génération de token)
    public Users authenticateUser(String username, String password) {
        // Recherche l'utilisateur par son username
        Optional<Users> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Nom d'utilisateur ou mot de passe incorrect !");
        }

        Users user = optionalUser.get();

        // Vérifie si le mot de passe correspond
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Nom d'utilisateur ou mot de passe incorrect !");
        }

        // Retourne l'utilisateur authentifié sans générer de token
        return user;
    }
}

