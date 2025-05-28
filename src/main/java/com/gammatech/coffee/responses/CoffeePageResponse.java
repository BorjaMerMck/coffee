package com.gammatech.coffee.responses;

import java.util.List;

import com.gammatech.coffee.models.Coffee;

public class CoffeePageResponse {
    private List<Coffee> coffees;
    private int totalElements;
    private int totalPages;
    private int currentPage;

    public CoffeePageResponse(List<Coffee> coffees, int totalElements, int totalPages, int currentPage) {
        this.coffees = coffees;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;

}}
