package com.gammatech.coffee.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gammatech.coffee.exceptions.CoffeeOrderDuplicatedException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Coffee;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.models.Order;
import com.gammatech.coffee.models.OrderItem;
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
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido  con el id '" + orderId + "' no encontrado"));
    }

    @Transactional
    public Order createOrder(Order orderRequest) {
        Customer customer = validateCustomer(orderRequest.getCustomer());
    
        Order order = new Order(); // Primero creamos la orden
        order.setCustomer(customer);
        order.setDateOrder(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
    
        List<OrderItem> orderItems = new ArrayList<>();
        Set<Long> coffeeIds = new HashSet<>();
    
        for (OrderItem item : orderRequest.getItems()) {
            OrderItem newItem = validateItem(item, coffeeIds,order);
            orderItems.add(newItem);
        }
    
        order.setItems(orderItems);
        order.setTotal(order.calculateTotal());
        return orderRepository.save(order);
      
    }
   

    @Transactional
    public Order updateOrder(Long id, Order orderRequest) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con ID: " + id + " no existe"));

        if (existingOrder.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden modificar pedidos en estado PENDING");
        }
    
        Customer customer = validateCustomer(orderRequest.getCustomer());
        existingOrder.setCustomer(customer);
    
        // Limpiar la lista anterior
        existingOrder.getItems().clear();
    
        // Añadir los nuevos items, validándolos de nuevo si hace falta
        for (OrderItem item : orderRequest.getItems()) {
            OrderItem newItem = validateItem(item, new HashSet<>(), existingOrder);
            existingOrder.getItems().add(newItem);
        }
    
        existingOrder.setTotal(existingOrder.calculateTotal());
        return orderRepository.save(existingOrder);
    }
    

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        // Validar que existe el order id
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con ID: " + id + " no existe"));
        if (status == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo");
        }
        existingOrder.setOrderStatus(status);
        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con ID: " + id + " no existe"));
        orderRepository.delete(existingOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo");
        }
        return orderRepository.findByOrderStatus(status);
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        // Verificar que el cliente existe
        customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("No existe el cliente con ID: " + customerId));
        return orderRepository.findAllByCustomerId(customerId);
    }

    // METODOS

    private OrderItem validateItem(OrderItem item, Set<Long> coffeeIds, Order order) {
        // que items tiene el orderitem
        // 1. coffee
        // 2. quantity

        Coffee coffee = validateCoffee(item.getCoffee());
        int quantity = validateQuantity(item.getQuantity());
    
        // validacion de que el cafe no se encuentre ya en el Set<Long> coffeeIds
        if (!coffeeIds.add(coffee.getId())) {
            throw new CoffeeOrderDuplicatedException("No se permiten cafes duplicados");
        }
    
        OrderItem newItem = new OrderItem();
        newItem.setCoffee(coffee);
        newItem.setQuantity(quantity);
        newItem.setOrder(order);
        newItem.setSubtotal(coffee.getPrice() * quantity);
        return newItem;
    }

    private Customer validateCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("El cliente del pedido es obligatorio");
        }
    
        // Devolvemos el cliente completo desde la base de datos
        return customerRepository.findById(customer.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No existe el cliente con ID: " + customer.getId()));
    }

    
    private Coffee validateCoffee(Coffee coffee) {
        // validamos que el coffee no sea nulo o sin id
        if (coffee == null || coffee.getId() == null) {
            throw new IllegalArgumentException("Debe haber al menos un café");
        }
    
        // Devolvemos el café completo desde la base de datos
        return coffeeRepository.findById(coffee.getId())
            .orElseThrow(() -> new ResourceNotFoundException("No existe café con ID: " + coffee.getId()));
    }
    

    private int validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        return quantity;
    }

 

}
