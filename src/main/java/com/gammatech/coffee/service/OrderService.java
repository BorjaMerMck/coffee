/**
 * Servicio que gestiona las operaciones relacionadas con los pedidos (orders) en el sistema.
 * Proporciona métodos para crear, actualizar, eliminar y consultar pedidos, así como validar
 * sus componentes (clientes, cafés, cantidades).
 */
package com.gammatech.coffee.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gammatech.coffee.repository.OrderRepository;
import com.gammatech.coffee.exceptions.CoffeeOrderDuplicatedException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Coffee;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.models.Order;
import com.gammatech.coffee.models.OrderItem;
import com.gammatech.coffee.models.OrderStatus;
import com.gammatech.coffee.repository.CoffeeRepository;
import com.gammatech.coffee.repository.CustomerRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CoffeeRepository coffeeRepository;
    private final CustomerRepository customerRepository;

    /**
     * Constructor que inicializa los repositorios necesarios para el servicio.
     * @param orderRepository Repositorio para operaciones con pedidos
     * @param coffeeRepository Repositorio para operaciones con cafés
     * @param customerRepository Repositorio para operaciones con clientes
     */
    public OrderService(OrderRepository orderRepository, CoffeeRepository coffeeRepository,
            CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.coffeeRepository = coffeeRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Obtiene todos los pedidos existentes en el sistema.
     * @return Lista de todos los pedidos
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Busca un pedido específico por su ID.
     * @param orderId ID del pedido a buscar
     * @return El pedido encontrado
     * @throws ResourceNotFoundException si no se encuentra el pedido
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido  con el id '" + orderId + "' no encontrado"));
    }

    /**
     * Crea un nuevo pedido en el sistema.
     * @param orderRequest Pedido a crear con sus detalles
     * @return El pedido creado y guardado
     * @throws IllegalArgumentException si los datos del pedido son inválidos
     * @throws ResourceNotFoundException si no se encuentran los recursos relacionados
     * @throws CoffeeOrderDuplicatedException si hay cafés duplicados en el pedido
     */
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
        return orderRepository.save(order);
    }
    
    /**
     * Actualiza un pedido existente.
     * @param id ID del pedido a actualizar
     * @param orderRequest Nuevos datos del pedido
     * @return El pedido actualizado
     * @throws ResourceNotFoundException si no se encuentra el pedido
     */
    public Order updateOrder(Long id, Order orderRequest) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con ID: " + id + " no existe"));

        // Validacion del orderRequest
        Customer customer = validateCustomer(orderRequest.getCustomer());

        // validateItems(orderRequest.getItems());

        existingOrder.setCustomer(customer);
        existingOrder.setItems(orderRequest.getItems());
        existingOrder.setTotal(orderRequest.calculateTotal());

        return orderRepository.save(existingOrder);
    }

    /**
     * Actualiza el estado de un pedido.
     * @param id ID del pedido a actualizar
     * @param status Nuevo estado del pedido
     * @return El pedido con el estado actualizado
     * @throws ResourceNotFoundException si no se encuentra el pedido
     */
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    /**
     * Elimina un pedido del sistema.
     * @param id ID del pedido a eliminar
     * @throws ResourceNotFoundException si no se encuentra el pedido
     */
    public void deleteOrder(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con ID: " + id + " no existe"));
        orderRepository.delete(existingOrder);
    }

    // METODOS DE VALIDACIÓN

    /**
     * Valida un ítem de pedido y verifica que no haya cafés duplicados.
     * @param item Ítem a validar
     * @param coffeeIds Set de IDs de cafés para verificar duplicados
     * @param order Pedido al que pertenece el ítem
     * @return El ítem validado
     * @throws CoffeeOrderDuplicatedException si el café ya existe en el pedido
     */
    private OrderItem validateItem(OrderItem item, Set<Long> coffeeIds, Order order) {
        Coffee coffee = validateCoffee(item.getCoffee());
        int quantity = validateQuantity(item.getQuantity());
    
        if (!coffeeIds.add(coffee.getId())) {
            throw new CoffeeOrderDuplicatedException("No se permiten cafes duplicados");
        }
    
        OrderItem newItem = new OrderItem();
        newItem.setCoffee(coffee);
        newItem.setQuantity(quantity);
        return newItem;
    }

    /**
     * Valida que el cliente exista en el sistema.
     * @param customer Cliente a validar
     * @return El cliente validado
     * @throws IllegalArgumentException si el cliente es nulo o no tiene ID
     * @throws ResourceNotFoundException si el cliente no existe en el sistema
     */
    private Customer validateCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("El cliente del pedido es obligatorio");
        }

        if (!customerRepository.existsById(customer.getId())) {
            throw new ResourceNotFoundException("No existe el cliente con ID: " + customer.getId());
        }
        return customer;
    }

    /**
     * Valida que el café exista en el sistema.
     * @param coffee Café a validar
     * @return El café validado
     * @throws IllegalArgumentException si el café es nulo o no tiene ID
     * @throws ResourceNotFoundException si el café no existe en el sistema
     */
    private Coffee validateCoffee(Coffee coffee) {
        if (coffee == null || coffee.getId() == null) {
            throw new IllegalArgumentException("Debe haber al menos un cafe");
        }

        if (!coffeeRepository.existsById(coffee.getId())) {
            throw new ResourceNotFoundException("No existe café con ID: " + coffee.getId());
        }
        return coffee;
    }

    /**
     * Valida que la cantidad de café sea válida.
     * @param quantity Cantidad a validar
     * @return La cantidad validada
     * @throws IllegalArgumentException si la cantidad es menor o igual a cero
     */
    private int validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        return quantity;
    }

}
