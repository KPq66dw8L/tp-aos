package com.aos.tp.orderService.controller;

import com.aos.tp.orderService.model.Order;
import com.aos.tp.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint pour créer une nouvelle commande
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order orderRequest) {
        Order newOrder = orderService.createOrder(orderRequest.getUserId(), orderRequest.getProduct(), orderRequest.getQuantity());
        return ResponseEntity.ok(newOrder);  // Retourne la commande créée
    }

    // Endpoint pour récupérer toutes les commandes
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);  // Retourne toutes les commandes
    }

    // Endpoint pour récupérer une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());  // Retourne 404 si la commande n'existe pas
    }

    // Endpoint pour récupérer les commandes d'un utilisateur spécifique
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);  // Retourne les commandes de l'utilisateur
    }
}

