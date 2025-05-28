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

    // select * from coffee
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    public Coffee getByIdCoffee(Long coffeeId) {
        return coffeeRepository.findById(coffeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Cafe  con el id '" + coffeeId + "' no encontrado"));

    }

    public Coffee createCoffee(Coffee coffeeRequest) {
        // campo unico
        if (coffeeRepository.existsByName(coffeeRequest.getName())) {
            throw new ResourceAlreadyExistsException("Ya existe un café con el nombre: " + coffeeRequest.getName());
        }
        validateData(coffeeRequest);
        return coffeeRepository.save(coffeeRequest);
    }

    public Coffee updateCoffee(Long coffeeId, Coffee coffeeRequest) {
        Coffee existingCoffee = coffeeRepository.findById(coffeeId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId));

        validateData(coffeeRequest);

        if (!existingCoffee.getName().equals(coffeeRequest.getName()) && 
            coffeeRepository.existsByName(coffeeRequest.getName())) {
            throw new ResourceAlreadyExistsException("Ya existe un café con el nombre: " + coffeeRequest.getName());
        }

        coffeeRequest.setId(coffeeId); // Asegurar que la ID sea la correcta
        return coffeeRepository.save(coffeeRequest);
    }

    public Coffee updateCoffeeImageUrl(Long coffeeId, String imageUrl) {
        // Verificar si existe el café
        Coffee existingCoffee = coffeeRepository.findById(coffeeId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId));
        // Validar que el imageUrl nuevo no sea nulo ni vacío

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva URL de la imagen no puede estar vacía");
        }
        // Actualizar solo el campo imageUrl
        existingCoffee.setImageUrl(imageUrl);
        return coffeeRepository.save(existingCoffee);
    }

    public void deleteCoffee(Long coffeeId) {
        Coffee deleteCoffee = coffeeRepository.findById(coffeeId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId));
        coffeeRepository.delete(deleteCoffee);
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
