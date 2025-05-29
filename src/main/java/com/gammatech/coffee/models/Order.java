package com.gammatech.coffee.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime dateOrder;

    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items;

    private double total;
    

    /*
     * Ejemplo de JSON:
     * {
     *     "id": 1,
     *     "customerId": 1,
     *     "dateOrder": "2025-05-28T10:00:00",
     *     "items": [
     *         {
     *             "coffeeId": 1,
     *             "quantity": 5,
     *             "subtotal": 10.0  // precio del café * cantidad
     *         },
     *         {
     *             "coffeeId": 2, 
     *             "quantity": 10,
     *             "subtotal": 50.0
     *         }
     *     ],
     *     "total": 60.0
     * }
     */
    public Order() {
    }

    public Order( Customer customer, LocalDateTime dateOrder, List<OrderItem> items) {
        this.customer = customer;
        this.dateOrder = dateOrder;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getDateOrder() {
        return dateOrder;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDateOrder(LocalDateTime dateOrder) {
        this.dateOrder = dateOrder;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // metodo para calcular el total de la orden
    public double calculateTotal() {
        return items.stream()
            .mapToDouble(OrderItem::calculateSubtotal)
            .sum();
    }

    
   

    
}