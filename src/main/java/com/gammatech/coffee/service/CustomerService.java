package com.gammatech.coffee.service;

import java.util.List;

import com.gammatech.coffee.models.Coffee;
import org.springframework.stereotype.Service;

import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Page<Customer> getAllPageable(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return customerRepository.findAll(pageable);
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente  con el id '" + customerId + "' no encontrado"));
    }

    public Customer createCustomer(Customer customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Ya existe un cliente con el email: " + customerRequest.getEmail());
        }
        validateCustomer(customerRequest);
        return customerRepository.save(customerRequest);
    }

    public Customer updateCustomer(Long customerId, Customer customerRequest) {
        Customer existingCustomer= customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con ID: " + customerId));
        validateCustomer(customerRequest);

        if (!existingCustomer.getEmail().equals(customerRequest.getEmail()) &&
                customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Ya existe un cliente con el email: " + customerRequest.getEmail());
        }
        existingCustomer.setName(customerRequest.getName());
        existingCustomer.setEmail(customerRequest.getEmail());
        existingCustomer.setPhone(customerRequest.getPhone());
        return customerRepository.save(existingCustomer);
    }

    public Customer updateCustomerEmail(Long customerId, String email) {
         // Verificar si existe el café
         Customer existingCustomer = customerRepository.findById(customerId)
                 .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con ID: " + customerId));

         if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email esta mal construido");
        }

      Customer emailExists = customerRepository.findByEmail(email);
      if (emailExists != null && !emailExists.getId().equals(customerId)) {
        throw new ResourceAlreadyExistsException("Ya existe un cliente con el email: " + email);
      }
         // Actualizar solo el campo email
         existingCustomer.setEmail(email);
         return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long customerId) {
        Customer deleteCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con ID: " + customerId));
        customerRepository.delete(deleteCustomer);
    }


    // METODOS
    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre y email del cliente son obligatorios");
        }
    }
}