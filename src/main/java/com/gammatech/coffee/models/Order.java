package com.gammatech.coffee.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Customer customer;
    private List<Coffee> items;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    // Constructor vacío
    public Order() {}

    // Constructor completo
    public Order(Long id, Customer customer, List<Coffee> items, double totalPrice, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Coffee> getItems() {
        return items;
    }

    public void setItems(List<Coffee> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
