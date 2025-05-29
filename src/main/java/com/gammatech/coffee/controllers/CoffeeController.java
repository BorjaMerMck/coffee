package com.gammatech.coffee.controllers;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Coffee>> getAllCoffees() {
        List<Coffee> coffees = coffeeService.getAllCoffees();
        if (coffees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coffees);
    }

    @GetMapping()
    public ResponseEntity<CoffeePageResponse> getCoffees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<Coffee> coffeesPage = coffeeService.getAllPageable(page, size);
        CoffeePageResponse coffeePageResponse = new CoffeePageResponse(
                coffeesPage.getContent(),
                (int) coffeesPage.getTotalElements(),
                coffeesPage.getTotalPages(),
                coffeesPage.getNumber());
        return ResponseEntity.ok(coffeePageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coffee> getCoffeeById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        return ResponseEntity.ok(coffee);
    }

    @PostMapping
    public ResponseEntity<Coffee> addCoffee(@RequestBody Coffee coffeeRequest) {
        Coffee savedCoffee = coffeeService.createCoffee(coffeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCoffee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coffee> updateCoffee(@PathVariable Long id, @RequestBody Coffee coffeeRequest) {
        Coffee updatedCoffee = coffeeService.updateCoffee(id, coffeeRequest);
        return ResponseEntity.ok(updatedCoffee);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Coffee> updateCoffeeImageUrl(@PathVariable Long id, @RequestParam String imageUrl) {
        Coffee updatedCoffee = coffeeService.updateCoffeeImageUrl(id, imageUrl);
        return ResponseEntity.ok(updatedCoffee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoffee(@PathVariable Long id) {
        coffeeService.deleteCoffee(id);
        return ResponseEntity.ok("Café eliminado correctamente");
    }
}
