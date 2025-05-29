package com.gammatech.coffee.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.gammatech.coffee.models.Order;
import com.gammatech.coffee.models.OrderStatus;
import com.gammatech.coffee.service.OrderService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomer(customerId);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order orderRequest) {
        Order orderCreated = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderRequest) {
        Order orderUpdated = orderService.updateOrder(id, orderRequest);
        return ResponseEntity.ok(orderUpdated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order orderUpdated = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(orderUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Pedido eliminado correctamente");
    }

}
    

