package com.gammatech.coffee.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Coffee;
import com.gammatech.coffee.responses.CoffeePageResponse;
import com.gammatech.coffee.service.CoffeeService;

@RestController
@RequestMapping("/api/coffees")
@CrossOrigin(origins = "*")
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCoffees() {
        List<Coffee> coffees = coffeeService.getAllCoffees();
        if (coffees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coffees);
    }

    @GetMapping()
    public ResponseEntity<?> getCoffees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<Coffee> coffeesPage = coffeeService.getAllPageable(page, size);
        CoffeePageResponse coffeePageResponse = new CoffeePageResponse(
                coffeesPage.getContent(),
                (int) coffeesPage.getTotalElements(),
                coffeesPage.getTotalPages(),
                coffeesPage.getNumber());
        return ResponseEntity.status(HttpStatus.OK).body(coffeePageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoffeeById(@PathVariable Long id) {
        try {
            Coffee coffee = coffeeService.getCoffeeById(id);
            return ResponseEntity.ok(coffee);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addCoffee(@RequestBody Coffee coffeeRequest) {
        try {
            Coffee savedCoffee = coffeeService.createCoffee(coffeeRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCoffee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoffee(@PathVariable Long id, @RequestBody Coffee coffeeRequest) {
        try {
            Coffee updatedCoffee = coffeeService.updateCoffee(id, coffeeRequest);
            return ResponseEntity.ok(updatedCoffee);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Actualizar solamente el campo de imageUrl
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCoffeePatch(@PathVariable Long id, @RequestParam String imageUrl) {
        try {
            Coffee updatedCoffee = coffeeService.updateCoffeeImageUrl(id, imageUrl);
            return ResponseEntity.ok(updatedCoffee);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoffee(@PathVariable Long id) {
        try {
            coffeeService.deleteCoffee(id);
            return ResponseEntity.ok("Café eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
