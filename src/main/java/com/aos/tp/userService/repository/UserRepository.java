package com.aos.tp.userService.repository;

import com.aos.tp.userService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // Méthode pour trouver un utilisateur par son username
    Optional<Users> findByUsername(String username);
}
