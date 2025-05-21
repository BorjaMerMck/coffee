package com.gammatech.coffee.models;

public class Coffee {
    private Long id;
    private String nombre;
    private Double precio;
    private String imageUrl;

    public Coffee(Long id, String nombre, Double precio, String imageUrl) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }       
}
