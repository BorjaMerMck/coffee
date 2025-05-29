package com.gammatech.coffee.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa un pedido en el sistema.
 * Esta clase mapea la tabla 'orders' en la base de datos.
 */
@Entity
@Table(name = "orders")
public class Order {
    
    /**
     * Identificador único del pedido.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Cliente que realiza el pedido.
     * Relación muchos a uno con la entidad Customer.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Fecha y hora en que se realizó el pedido.
     */
    private LocalDateTime dateOrder;

    /**
     * Estado actual del pedido.
     */
    private OrderStatus orderStatus;

    /**
     * Lista de items del pedido.
     * Relación uno a muchos con la entidad OrderItem.
     * Se cargan de manera ansiosa (EAGER) y se eliminan en cascada.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items;

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
     *             "subtotal": 10.0  // precio del cafÃ© * cantidad
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

    /**
     * Constructor por defecto.
     */
    public Order() {
    }

    /**
     * Constructor con parámetros para crear un nuevo pedido.
     * @param customer Cliente que realiza el pedido
     * @param items Lista de items del pedido
     */
    public Order(Customer customer, List<OrderItem> items) {
        this.customer = customer;
        this.items = items;
    }

    /**
     * Obtiene el ID del pedido.
     * @return el ID del pedido
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el cliente del pedido.
     * @return el cliente del pedido
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Obtiene la fecha y hora del pedido.
     * @return la fecha y hora del pedido
     */
    public LocalDateTime getDateOrder() {
        return dateOrder;
    }

    /**
     * Obtiene la lista de items del pedido.
     * @return la lista de items del pedido
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Establece el cliente del pedido.
     * @param customer el nuevo cliente a establecer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Establece la fecha y hora del pedido.
     * @param dateOrder la nueva fecha y hora a establecer
     */
    public void setDateOrder(LocalDateTime dateOrder) {
        this.dateOrder = dateOrder;
    }

    /**
     * Establece la lista de items del pedido.
     * @param items la nueva lista de items a establecer
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Obtiene el estado del pedido.
     * @return el estado del pedido
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Establece el estado del pedido.
     * @param orderStatus el nuevo estado a establecer
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Calcula el total del pedido sumando los subtotales de cada item.
     * @return el total del pedido
     */
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void setTotal(double calculateTotal) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTotal'");
    }   

}