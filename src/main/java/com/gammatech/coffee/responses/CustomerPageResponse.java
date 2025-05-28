package com.gammatech.coffee.responses;

import java.util.List;

import com.gammatech.coffee.models.Customer;

public class CustomerPageResponse {
    private List<Customer> customers;
    private int totalElements;
    private int totalPages;
    private int currentPage;

    public CustomerPageResponse(List<Customer> customers, int totalElements, int totalPages, int currentPage) {
        this.customers = customers;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
} 