package com.gammatech.coffee.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un café en el sistema.
 * Esta clase mapea la tabla 'coffee' en la base de datos.
 */
@Entity
@Table(name = "coffee")
public class Coffee {
    
    /**
     * Identificador único del café.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    /**
     * Nombre del café.
     * Debe ser único y no puede ser nulo.
     */
    @Column(unique = true , nullable = false)
    private String name;
    
    /**
     * Precio del café.
     */
    private Double price;

    /**
     * URL de la imagen del café.
     */
    private String imageUrl;

    /**
     * Constructor por defecto.
     */
    public Coffee() {
    }

    /**
     * Constructor con parámetros para crear un nuevo café.
     * @param name Nombre del café
     * @param price Precio del café
     * @param imageUrl URL de la imagen del café
     */
    public Coffee(String name, Double price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * Obtiene el ID del café.
     * @return el ID del café
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del café.
     * @param id el nuevo ID a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del café.
     * @return el nombre del café
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del café.
     * @param name el nuevo nombre a establecer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el precio del café.
     * @return el precio del café
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Establece el precio del café.
     * @param price el nuevo precio a establecer
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Obtiene la URL de la imagen del café.
     * @return la URL de la imagen del café
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Establece la URL de la imagen del café.
     * @param imageUrl la nueva URL de imagen a establecer
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
