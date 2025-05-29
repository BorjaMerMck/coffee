package com.gammatech.coffee.responses;

import java.util.List;

import com.gammatech.coffee.models.Coffee;

/**
 * Clase que representa la respuesta paginada de cafés.
 * Esta clase se utiliza para enviar información sobre una página de cafés,
 * incluyendo la lista de cafés y metadatos de paginación.
 */
public class CoffeePageResponse {
    /** Lista de cafés en la página actual */
    private List<Coffee> coffees;
    /** Número total de elementos en todas las páginas */
    private int totalElements;
    /** Número total de páginas disponibles */
    private int totalPages;
    /** Número de la página actual (comenzando desde 0) */
    private int currentPage;

    /**
     * Constructor para crear una nueva respuesta paginada de cafés.
     * @param coffees Lista de cafés en la página actual
     * @param totalElements Número total de elementos en todas las páginas
     * @param totalPages Número total de páginas disponibles
     * @param currentPage Número de la página actual
     */
    public CoffeePageResponse(List<Coffee> coffees, int totalElements, int totalPages, int currentPage) {
        this.coffees = coffees;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    /**
     * Obtiene la lista de cafés de la página actual.
     * @return Lista de cafés
     */
    public List<Coffee> getCoffees() {
        return coffees;
    }

    /**
     * Obtiene el número total de elementos en todas las páginas.
     * @return Número total de elementos
     */
    public int getTotalElements() {
        return totalElements;
    }

    /**
     * Obtiene el número total de páginas disponibles.
     * @return Número total de páginas
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Obtiene el número de la página actual.
     * @return Número de la página actual
     */
    public int getCurrentPage() {
        return currentPage;

}}
