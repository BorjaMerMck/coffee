package com.gammatech.coffee.controllers;

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

/**
 * Controlador REST que maneja las operaciones CRUD para la entidad Coffee.
 * Proporciona endpoints para gestionar cafés en el sistema.
 */
@RestController
@RequestMapping("/api/coffees")
@CrossOrigin(origins = "*")
public class CoffeeController {

    private final CoffeeService coffeeService;

    /**
     * Constructor que inyecta el servicio de café.
     * @param coffeeService Servicio que maneja la lógica de negocio de los cafés
     */
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    /**
     * Obtiene todos los cafés disponibles.
     * @return ResponseEntity con la lista de cafés o sin contenido si no hay cafés
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllCoffees() {
        List<Coffee> coffees = coffeeService.getAllCoffees();
        if (coffees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coffees);
    }

    /**
     * Obtiene una página de cafés.
     * @param page número de página (por defecto 0)
     * @param size tamaño de la página (por defecto 2)
     * @return ResponseEntity con la respuesta paginada de cafés
     */
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

    /**
     * Obtiene un café por su ID.
     * @param id ID del café a buscar
     * @return ResponseEntity con el café encontrado o mensaje de error si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoffeeById(@PathVariable Long id) {
        try {
            Coffee coffee = coffeeService.getCoffeeById(id);
            return ResponseEntity.ok(coffee);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Crea un nuevo café.
     * @param coffeeRequest Datos del café a crear
     * @return ResponseEntity con el café creado o mensaje de error si hay problemas
     */
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

    /**
     * Actualiza un café existente.
     * @param id ID del café a actualizar
     * @param coffeeRequest Nuevos datos del café
     * @return ResponseEntity con el café actualizado o mensaje de error si hay problemas
     */
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

    /**
     * Actualiza solo la URL de la imagen de un café.
     * @param id ID del café a actualizar
     * @param imageUrl Nueva URL de la imagen
     * @return ResponseEntity con el café actualizado o mensaje de error si hay problemas
     */
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

    /**
     * Elimina un café por su ID.
     * @param id ID del café a eliminar
     * @return ResponseEntity con mensaje de éxito o error si el café no existe
     */
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
