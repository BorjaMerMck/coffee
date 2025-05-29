package com.gammatech.coffee.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // muchos items pueden pertenecer a un unico pedido
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double subtotal;

    public OrderItem() {
    }

    public OrderItem(Order order, Coffee coffee, int quantity) {
        this.order = order;
        this.coffee = coffee;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // metodo para calcular el subtotal de la orden
    public double calculateSubtotal() {
        return coffee.getPrice() * quantity;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}