package com.aos.tp.orderService.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identifiant unique de la commande

    private Long userId;  // Identifiant de l'utilisateur qui a passé la commande
    private String product;  // Nom ou description du produit
    private int quantity;  // Quantité commandée

    // Constructeurs
    public Order() {
    }

    public Order(Long userId, String product, int quantity) {
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
