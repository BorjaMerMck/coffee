package com.gammatech.coffee.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gammatech.coffee.exceptions.CoffeeOrderDuplicatedException;
import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Coffee;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.models.Order;
import com.gammatech.coffee.models.OrderStatus;
import com.gammatech.coffee.repository.CoffeeRepository;
import com.gammatech.coffee.repository.CustomerRepository;
import com.gammatech.coffee.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CoffeeRepository coffeeRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CoffeeRepository coffeeRepository,
            CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.coffeeRepository = coffeeRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.getById(id);
        if (order == null) {
            throw new ResourceNotFoundException("No se encontró el pedido con ID: " + id);
        }
        return order;
    }

    public Order createOrder(Order orderRequest) {

  /*
         * {
         * "id": 1,
         * "customer": {
         * "id": 1
         * },
         * "coffees": [
         * {
         * "id": 1,
         * },
         * {
         * "id": 2,
         * }
         * ]
         */

         // Validacion del orderRequest
        if (orderRequest.getId() == null) {
            throw new IllegalArgumentException("El ID del pedido no puede ser nulo");
        }
    
        if (orderRepository.existsById(orderRequest.getId())) {
            throw new ResourceAlreadyExistsException("Ya existe un pedido con el ID: " + orderRequest.getId());
        }

        // Validacion del customer y los cafes
        validateCustomer(orderRequest.getCustomer());
        validateCoffees(orderRequest.getItems());

    
        orderRequest.setTotalPrice(calculateTotal(orderRequest.getItems()));
        orderRequest.setOrderDate(LocalDateTime.now());
        orderRequest.setOrderStatus(OrderStatus.PENDIENTE);
    
        return orderRepository.save(orderRequest);
    }

    public Order updateOrder(Long id, Order orderRequest) {
        Order existingOrder = orderRepository.getById(id);
        if (existingOrder == null) {
            throw new ResourceNotFoundException("El pedido con ID: " + id + " no existe");
        }
    
        // Validacion del orderRequest
        if (orderRequest.getId() == null) {
            throw new IllegalArgumentException("El ID del pedido no puede ser nulo");
        }
    
        if (!orderRequest.getId().equals(id)) {
            throw new IllegalArgumentException("El ID del pedido no puede cambiarse. Se esperaba: " + id);
        }
    
        validateCustomer(orderRequest.getCustomer());
    
        if (!existingOrder.getCustomer().getId().equals(orderRequest.getCustomer().getId())) {
            throw new ResourceAlreadyExistsException("No se puede cambiar el cliente del pedido con ID: " + id);
        }
    
        validateCoffees(orderRequest.getItems());
    
        orderRequest.setId(id);
        orderRequest.setTotalPrice(calculateTotal(orderRequest.getItems()));
        orderRequest.setOrderDate(LocalDateTime.now());
        orderRequest.setOrderStatus(existingOrder.getOrderStatus());
    
        return orderRepository.save(orderRequest);
    }

    public Order deleteOrder(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }



    // METODOS
    private void validateCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("El cliente del pedido es obligatorio");
        }
    
        if (!customerRepository.existsById(customer.getId())) {
            throw new ResourceNotFoundException("No existe el cliente con ID: " + customer.getId());
        }
    }



    private void validateCoffees(List<Coffee> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un café");
        }
    
        Set<Long> uniqueCoffeeIds = new HashSet<>();
        for (Coffee item : items) {
            if (item == null || item.getId() == null) {
                throw new IllegalArgumentException("Los cafés no pueden ser nulos o sin ID");
            }
    
            if (!coffeeRepository.existsById(item.getId())) {
                throw new ResourceNotFoundException("No se encontró el café con ID: " + item.getId());
            }
    
            if (!uniqueCoffeeIds.add(item.getId())) {
                throw new CoffeeOrderDuplicatedException("El pedido contiene cafés duplicados con ID: " + item.getId());
            }
        }
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    private double calculateTotal(List<Coffee> items) {
        return items.stream()
                    .mapToDouble(item -> coffeeRepository.getById(item.getId()).getPrice())
                    .sum();
    }

}
