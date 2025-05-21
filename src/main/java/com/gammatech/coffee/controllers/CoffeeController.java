package com.gammatech.coffee.controllers;

import java.util.ArrayList;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gammatech.coffee.models.Coffee;

@RestController
@RequestMapping("/api/coffees")
@CrossOrigin(origins = "*")
public class CoffeeController {

    private List<Coffee> coffees = new ArrayList<>();


    @GetMapping // select * from coffees
    public ResponseEntity<?> getAllCoffees() {
        try {
            return ResponseEntity.ok(coffees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // select * from coffees where id = ?
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoffeeById(@PathVariable Long id) {
        System.out.println("id: " + id);
        for (Coffee coffee : coffees) {
            if (coffee.getId().equals(id)) {
                System.out.println("cafe encontrado");
                return ResponseEntity.ok(coffee);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // insert into coffees (name, price, imageUrl) values (?, ?, ?)
    @PostMapping
    public ResponseEntity<?> createCoffee(@RequestBody Coffee coffee) {
        try {
            coffees.add(coffee);
            return ResponseEntity.status(HttpStatus.CREATED).body(coffee);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // update coffees set name = ?, price = ?, imageUrl = ? where id = ?
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoffee(@PathVariable Long id, @RequestBody Coffee coffeeRequest) {
        for (Coffee coffee : coffees) {
            if (coffee.getId().equals(id)) {
                coffee.setNombre(coffeeRequest.getNombre());
                coffee.setPrecio(coffeeRequest.getPrecio());
                coffee.setImageUrl(coffeeRequest.getImageUrl());
                return ResponseEntity.ok(coffee); // 200 OK con el café actualizado
            }
        }
        return ResponseEntity.notFound().build(); // 404 Not Found si no existe el café
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteCoffee(@PathVariable Long id) {
        for (Coffee coffee : coffees) {
            if (coffee.getId().equals(id)) {
                coffees.remove(coffee);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    
}