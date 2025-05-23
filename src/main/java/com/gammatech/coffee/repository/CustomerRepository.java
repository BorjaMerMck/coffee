package com.gammatech.coffee.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gammatech.coffee.models.Customer;

@Repository
public class CustomerRepository {
    private final Map<Long, Customer> customers = new HashMap<>(); 
    // clave: id, valor: customer
    // campo unico :email

    public Customer save(Customer customer) {
        customers.put(customer.getId(), customer);
        return customers.get(customer.getId());
    }
    

    public Customer delete(Long customerId) {
        return customers.remove(customerId);
    }

    public List<Customer> getAll() {
        return List.copyOf(customers.values());
    }

    public Customer getById(Long customerId) {
        return customers.get(customerId);
    }

    public boolean existsById(Long customerId) {
        return customers.containsKey(customerId);
    }

    public boolean existsByEmail(String email) {
        return customers.values().stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
    }
    public Customer findByEmail(String email) {
        return customers.values().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
