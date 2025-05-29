package com.gammatech.coffee.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Entidad que representa un ítem de pedido en el sistema.
 * Esta clase mapea la tabla 'order_items' en la base de datos.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    /**
     * Identificador único del ítem de pedido.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Pedido al que pertenece este ítem.
     * Relación muchos a uno con la entidad Order.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Café asociado a este ítem de pedido.
     * Relación muchos a uno con la entidad Coffee.
     */
    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;

    /**
     * Cantidad de cafés en este ítem.
     * No puede ser nulo.
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Constructor por defecto.
     */
    public OrderItem() {
    }

    /**
     * Constructor con parámetros para crear un nuevo ítem de pedido.
     * @param coffee El café asociado al ítem
     * @param quantity La cantidad de cafés
     */
    public OrderItem(Coffee coffee, int quantity) {
        this.coffee = coffee;
        this.quantity = quantity;
    }

    /**
     * Obtiene el ID del ítem de pedido.
     * @return el ID del ítem
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el pedido asociado.
     * @return el pedido al que pertenece este ítem
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Establece el pedido asociado.
     * @param order el pedido a establecer
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Obtiene el café asociado.
     * @return el café de este ítem
     */
    public Coffee getCoffee() {
        return coffee;
    }

    /**
     * Establece el café asociado.
     * @param coffee el café a establecer
     */
    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    /**
     * Obtiene la cantidad de cafés.
     * @return la cantidad de cafés en este ítem
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Establece la cantidad de cafés.
     * @param quantity la cantidad a establecer
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calcula el subtotal del ítem.
     * @return el subtotal calculado como precio del café multiplicado por la cantidad,
     *         o 0.0 si el café o su precio son nulos
     */
    public double getSubtotal() {
        if (coffee != null && coffee.getPrice() != null) {
            return coffee.getPrice() * quantity;
        } else {
            return 0.0;
        }
    }
}