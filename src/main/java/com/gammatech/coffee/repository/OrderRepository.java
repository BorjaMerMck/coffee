package com.gammatech.coffee.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gammatech.coffee.models.Order;

@Repository
public class OrderRepository {
    private final Map<Long, Order> orders = new HashMap<>();
    
    // clave: id, valor: order

    public Order save(Order order) {
        orders.put(order.getId(), order);
        return orders.get(order.getId());
    }

    public Order getById(Long orderId) {
        return orders.get(orderId);
    }

    public List<Order> getAll() {
        return List.copyOf(orders.values());
    }

    public boolean existsById(Long orderId) {
        return orders.containsKey(orderId);
    }

    public Order delete(Long orderId) {
        return orders.remove(orderId);
    }

    public boolean existsByCustomerId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByCustomerId'");
    }
}
