package com.gammatech.coffee.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.responses.CustomerPageResponse;
import com.gammatech.coffee.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping()
    public ResponseEntity<CustomerPageResponse> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<Customer> customersPage = customerService.getAllPageable(page, size);
        CustomerPageResponse customerPageResponse = new CustomerPageResponse(
                customersPage.getContent(),
                (int) customersPage.getTotalElements(),
                customersPage.getTotalPages(),
                customersPage.getNumber());
        return ResponseEntity.ok(customerPageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customerRequest) {
        Customer created = customerService.createCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerRequest) {
        Customer updated = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<Customer> updateCustomerEmail(@PathVariable Long id, @RequestParam String email) {
        Customer updated = customerService.updateCustomerEmail(id, email);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}