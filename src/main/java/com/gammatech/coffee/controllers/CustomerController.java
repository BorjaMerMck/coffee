package com.gammatech.coffee.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.Page;

import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.responses.CustomerPageResponse;
import com.gammatech.coffee.service.CustomerService;

/**
 * Controlador REST que maneja las operaciones CRUD para la entidad Customer.
 * Proporciona endpoints para gestionar clientes en el sistema.
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructor que inyecta el servicio de cliente.
     * @param customerService Servicio que maneja la lógica de negocio de los clientes
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Obtiene todos los clientes disponibles.
     * @return ResponseEntity con la lista de clientes o sin contenido si no hay clientes
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    /**
     * Obtiene una página de clientes.
     * @param page número de página (por defecto 0)
     * @param size tamaño de la página (por defecto 2)
     * @return ResponseEntity con la respuesta paginada de clientes
     */
    @GetMapping()
    public ResponseEntity<?> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<Customer> customersPage = customerService.getAllPageable(page, size);
        CustomerPageResponse customerPageResponse = new CustomerPageResponse(
                customersPage.getContent(),
                (int) customersPage.getTotalElements(),
                customersPage.getTotalPages(),
                customersPage.getNumber());
        return ResponseEntity.status(HttpStatus.OK).body(customerPageResponse);
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id Identificador único del cliente
     * @return ResponseEntity con el cliente encontrado o mensaje de error si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Crea un nuevo cliente en el sistema.
     * @param customerRequest Datos del cliente a crear
     * @return ResponseEntity con el cliente creado o mensaje de error si hay problemas
     */
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customerRequest) {
        try {
            Customer created = customerService.createCustomer(customerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Actualiza todos los datos de un cliente existente.
     * @param id Identificador único del cliente a actualizar
     * @param customerRequest Nuevos datos del cliente
     * @return ResponseEntity con el cliente actualizado o mensaje de error si hay problemas
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customerRequest) {
        try {
            Customer updated = customerService.updateCustomer(id, customerRequest);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Actualiza solo el email de un cliente existente.
     * @param id Identificador único del cliente
     * @param email Nuevo email del cliente
     * @return ResponseEntity con el cliente actualizado o mensaje de error si hay problemas
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCustomerPatch(@PathVariable Long id, @RequestParam String email) {
        try {
            Customer updated = customerService.updateCustomerEmail(id, email);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Elimina un cliente del sistema.
     * @param id Identificador único del cliente a eliminar
     * @return ResponseEntity con mensaje de éxito o error si el cliente no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
           customerService.deleteCustomer(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}