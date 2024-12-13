package com.aos.tp.orderService.service;

import com.aos.tp.orderService.model.Order;
import com.aos.tp.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Méthode pour créer une commande
    public Order createOrder(Long userId, String product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0.");
        }

        // Crée une nouvelle commande
        Order order = new Order(userId, product, quantity);
        return orderRepository.save(order); // Sauvegarde dans la base de données
    }

    // Méthode pour récupérer toutes les commandes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Méthode pour récupérer une commande par son ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Méthode pour récupérer les commandes d'un utilisateur spécifique
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
