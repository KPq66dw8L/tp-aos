package com.aos.tp.orderService.repository;

import com.aos.tp.orderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Méthode pour trouver les commandes d'un utilisateur spécifique
    List<Order> findByUserId(Long userId);
}
