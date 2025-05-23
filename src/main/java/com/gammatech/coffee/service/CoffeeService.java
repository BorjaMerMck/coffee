package com.gammatech.coffee.service;

import com.gammatech.coffee.repository.CoffeeRepository;
import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Coffee;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public List<Coffee> getAllCoffees() {
        return coffeeRepository.getAll();
    }

    public Coffee getByIdCoffee(Long coffeeId) {
        Coffee coffee = coffeeRepository.getById(coffeeId);
        if (coffee == null) {
            throw new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId);
        }
        return coffee;

    }

    public Coffee createCoffee(Coffee coffeeRequest) {
        if (coffeeRepository.existsById(coffeeRequest.getId())) {
            throw new ResourceAlreadyExistsException("Ya existe un café con el ID: " + coffeeRequest.getId());
        }
        // campo unico
        if (coffeeRepository.existsByName(coffeeRequest.getName())) {
            throw new ResourceAlreadyExistsException("Ya existe un café con el nombre: " + coffeeRequest.getName());
        }

        validateData(coffeeRequest);
        return coffeeRepository.save(coffeeRequest);
    }

    public Coffee updateCoffee(Long coffeeId, Coffee coffeeRequest) {
        if (!coffeeRepository.existsById(coffeeId)) {
            throw new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId);
        }

        validateData(coffeeRequest); // Primero validar datos JSON

        // Validar nombre único en otro método o aquí directamente

        for (Coffee existing : coffeeRepository.getAll()) {
            if (existing.getName().equalsIgnoreCase(coffeeRequest.getName()) &&
                    !existing.getId().equals(coffeeId)) {
                throw new ResourceAlreadyExistsException("Ya existe un café con el nombre: " + coffeeRequest.getName());
            }
        }

        coffeeRequest.setId(coffeeId); // Asegurar que la ID sea la correcta
        return coffeeRepository.save(coffeeRequest);
    }

    public Coffee updateCoffeeImageUrl(Long coffeeId, String imageUrl) {
        // Verificar si existe el café
        Coffee coffeeExists = coffeeRepository.getById(coffeeId);
        if (coffeeExists == null) {
            throw new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId);
        }
        // Validar que el imageUrl nuevo no sea nulo ni vacío
       
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva URL de la imagen no puede estar vacía");
        }
        // Actualizar solo el campo imageUrl
        coffeeExists.setImageUrl(imageUrl);
        return coffeeRepository.save(coffeeExists);
    }

    
    public Coffee deleteCoffee(Long coffeeId) {
        Coffee coffeeToDelete = coffeeRepository.getById(coffeeId);
        if (coffeeToDelete == null) {
            throw new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId);
        }
        return coffeeRepository.delete(coffeeToDelete.getId());
    }

    // METODOS
    private void validateData(Coffee coffee) {
        if (coffee.getName() == null || coffee.getName().trim().isEmpty() ||
                coffee.getImageUrl() == null || coffee.getImageUrl().trim().isEmpty() ||
                coffee.getPrice() <= 0) {
            throw new IllegalArgumentException("Datos inválidos");
        }
    }

}
