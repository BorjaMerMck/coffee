package com.gammatech.coffee.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import com.gammatech.coffee.exceptions.CoffeeOrderDuplicatedException;
import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Order;
import com.gammatech.coffee.models.OrderStatus;
import com.gammatech.coffee.service.OrderService;

/**
 * Controlador REST que maneja las operaciones CRUD para la entidad Order.
 * Proporciona endpoints para gestionar pedidos en el sistema.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * Constructor que inyecta el servicio de pedidos.
     * @param orderService Servicio que maneja la lógica de negocio de los pedidos
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Obtiene todos los pedidos disponibles.
     * @return ResponseEntity con la lista de pedidos o sin contenido si no hay pedidos
     */
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    /**
     * Obtiene un pedido por su ID.
     * @param id Identificador único del pedido
     * @return ResponseEntity con el pedido encontrado o mensaje de error si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Crea un nuevo pedido en el sistema.
     * @param orderRequest Datos del pedido a crear
     * @return ResponseEntity con el pedido creado o mensaje de error si hay problemas
     * @throws IllegalArgumentException si los datos del pedido son inválidos
     * @throws ResourceNotFoundException si no se encuentra algún recurso relacionado
     * @throws ResourceAlreadyExistsException si el pedido ya existe
     * @throws CoffeeOrderDuplicatedException si hay un café duplicado en el pedido
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order orderRequest) {
        try {
            Order orderCreated = orderService.createOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderCreated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (CoffeeOrderDuplicatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Actualiza un pedido existente.
     * @param id Identificador único del pedido
     * @param orderRequest Nuevos datos del pedido
     * @return ResponseEntity con el pedido actualizado o mensaje de error si hay problemas
     * @throws ResourceNotFoundException si no se encuentra el pedido
     * @throws ResourceAlreadyExistsException si hay conflicto con otro pedido existente
     * @throws CoffeeOrderDuplicatedException si hay un café duplicado en el pedido
     * @throws IllegalArgumentException si los datos del pedido son inválidos
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Order orderRequest) {
        try {
            Order orderUpdated = orderService.updateOrder(id, orderRequest);
            return ResponseEntity.ok(orderUpdated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (CoffeeOrderDuplicatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Actualiza el estado de un pedido existente.
     * @param id Identificador único del pedido
     * @param status Nuevo estado del pedido
     * @return ResponseEntity con el pedido actualizado o mensaje de error si hay problemas
     * @throws ResourceNotFoundException si no se encuentra el pedido
     * @throws IllegalArgumentException si el estado es inválido
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            Order orderUpdated = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(orderUpdated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Elimina un pedido del sistema.
     * @param id Identificador único del pedido a eliminar
     * @return ResponseEntity con mensaje de éxito o error si el pedido no existe
     * @throws ResourceNotFoundException si no se encuentra el pedido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Pedido eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
