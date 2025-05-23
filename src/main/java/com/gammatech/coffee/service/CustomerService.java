package com.gammatech.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAll();
    }

    public Customer getCustomerById(Long customerId) {
        Customer customer = customerRepository.getById(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("No se encontró el cliente con ID: " + customerId);
        }
        return customer;
    }

    public Customer createCustomer(Customer customerRequest) {
        if (customerRepository.existsById(customerRequest.getId())) {
            throw new ResourceAlreadyExistsException("Ya existe un cliente con el ID: " + customerRequest.getId());
        }
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Ya existe un cliente con el email: " + customerRequest.getEmail());
        }

        validateCustomer(customerRequest);
        return customerRepository.save(customerRequest);
    }

    public Customer updateCustomer(Long customerId, Customer customerRequest) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("No se encontró el cliente con ID: " + customerId);
        }

        validateCustomer(customerRequest);

        for (Customer existing : customerRepository.getAll()) {
            if (existing.getEmail().equalsIgnoreCase(customerRequest.getEmail()) &&
                !existing.getId().equals(customerId)) {
                throw new ResourceAlreadyExistsException("Ya existe un cliente con el email: " + customerRequest.getEmail());
            }
        }

        customerRequest.setId(customerId);
        return customerRepository.save(customerRequest);
    }

    public Customer deleteCustomer(Long customerId) {
        Customer customerToDelete = customerRepository.getById(customerId);
        if (customerToDelete == null) {
            throw new ResourceNotFoundException("No se encontró el cliente con ID: " + customerId);
        }
        return customerRepository.delete(customerToDelete.getId());
    }

    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre y email del cliente son obligatorios");
        }
    }

    public Customer updateCustomerEmail(Long id, String email) {
         // Verificar si existe el café
         Customer customerExists = customerRepository.getById(id);
         if (customerExists == null) {
             throw new ResourceNotFoundException("No se encontró el cliente con ID: " + id);
         }

         if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email esta mal construido");
        }

      Customer emailExists = customerRepository.findByEmail(email);
      if (emailExists != null && !emailExists.getId().equals(id)) {
        throw new ResourceAlreadyExistsException("Ya existe un cliente con el email: " + email);
      }
         // Actualizar solo el campo email
         customerExists.setEmail(email);
         return customerRepository.save(customerExists);
    }
}
