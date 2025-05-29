package com.gammatech.coffee.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un cliente en el sistema.
 * Esta clase mapea la tabla 'customer' en la base de datos.
 */
@Entity
@Table(name = "customer")
public class Customer {
    
    /**
     * Identificador único del cliente.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    /**
     * Nombre del cliente.
     */
    private String name;

    /**
     * Email del cliente.
     * Debe ser único y no puede ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Número de teléfono del cliente.
     */
    private String phone;
    
    /**
     * Constructor por defecto.
     */
    public Customer() {
    }

    /**
     * Constructor con parámetros para crear un nuevo cliente.
     * @param name Nombre del cliente
     * @param email Email del cliente
     * @param phone Número de teléfono del cliente
     */
    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Obtiene el ID del cliente.
     * @return el ID del cliente
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return el nombre del cliente
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el email del cliente.
     * @return el email del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     * @return el número de teléfono del cliente
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Establece el ID del cliente.
     * @param id el nuevo ID a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Establece el nombre del cliente.
     * @param name el nuevo nombre a establecer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Establece el email del cliente.
     * @param email el nuevo email a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Establece el número de teléfono del cliente.
     * @param phone el nuevo número de teléfono a establecer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

 
}