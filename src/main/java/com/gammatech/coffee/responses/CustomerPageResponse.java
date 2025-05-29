package com.gammatech.coffee.responses;

import java.util.List;

import com.gammatech.coffee.models.Customer;

/**
 * Clase que representa la respuesta paginada de clientes.
 * Esta clase encapsula la información necesaria para mostrar una página de clientes,
 * incluyendo la lista de clientes y los metadatos de paginación.
 */
public class CustomerPageResponse {
    /** Lista de clientes en la página actual */
    private List<Customer> customers;
    /** Número total de elementos en todas las páginas */
    private int totalElements;
    /** Número total de páginas disponibles */
    private int totalPages;
    /** Número de la página actual (comenzando desde 0) */
    private int currentPage;

    /**
     * Constructor que inicializa una respuesta paginada de clientes.
     * 
     * @param customers Lista de clientes para la página actual
     * @param totalElements Número total de clientes en todas las páginas
     * @param totalPages Número total de páginas disponibles
     * @param currentPage Número de la página actual
     */
    public CustomerPageResponse(List<Customer> customers, int totalElements, int totalPages, int currentPage) {
        this.customers = customers;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    /**
     * Obtiene la lista de clientes de la página actual.
     * @return Lista de clientes
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Obtiene el número total de clientes en todas las páginas.
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
    }
} 