package com.gammatech.coffee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Customer;
import com.gammatech.coffee.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Servicio que gestiona las operaciones relacionadas con los clientes.
 * Proporciona métodos para crear, leer, actualizar y eliminar clientes,
 * así como para realizar validaciones y manejar la paginación.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Constructor del servicio de clientes.
     * @param customerRepository Repositorio de clientes a inyectar
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Obtiene todos los clientes del sistema.
     * @return Lista con todos los clientes
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Obtiene una página de clientes según los parámetros especificados.
     * @param page Número de página (comenzando desde 0)
     * @param pageSize Tamaño de cada página
     * @return Página de clientes
     */
    public Page<Customer> getAllPageable(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return customerRepository.findAll(pageable);
    }

    /**
     * Busca un cliente por su ID.
     * @param customerId ID del cliente a buscar
     * @return Cliente encontrado
     * @throws ResourceNotFoundException si no se encuentra el cliente
     */
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente  con el id '" + customerId + "' no encontrado"));
    }

    /**
     * Crea un nuevo cliente en el sistema.
     * @param customerRequest Datos del cliente a crear
     * @return Cliente creado
     * @throws ResourceAlreadyExistsException si ya existe un cliente con el mismo email
     * @throws IllegalArgumentException si los datos del cliente son inválidos
     */
    public Customer createCustomer(Customer customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Ya existe un cliente con el email: " + customerRequest.getEmail());
        }
        validateCustomer(customerRequest);
        return customerRepository.save(customerRequest);
    }

    /**
     * Actualiza todos los datos de un cliente existente.
     * @param customerId ID del cliente a actualizar
     * @param customerRequest Nuevos datos del cliente
     * @return Cliente actualizado
     * @throws ResourceNotFoundException si no se encuentra el cliente
     * @throws ResourceAlreadyExistsException si el nuevo email ya está en uso
     * @throws IllegalArgumentException si los datos del cliente son inválidos
     */
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

    /**
     * Actualiza solo el email de un cliente existente.
     * @param customerId ID del cliente a actualizar
     * @param email Nuevo email del cliente
     * @return Cliente actualizado
     * @throws ResourceNotFoundException si no se encuentra el cliente
     * @throws IllegalArgumentException si el email es inválido
     * @throws ResourceAlreadyExistsException si el nuevo email ya está en uso
     */
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

    /**
     * Elimina un cliente del sistema.
     * @param customerId ID del cliente a eliminar
     * @throws ResourceNotFoundException si no se encuentra el cliente
     */
    public void deleteCustomer(Long customerId) {
        Customer deleteCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente con ID: " + customerId));
        customerRepository.delete(deleteCustomer);
    }

    /**
     * Valida los datos de un cliente.
     * @param customer Cliente a validar
     * @throws IllegalArgumentException si el nombre o email son nulos o vacíos
     */
    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty() ||
            customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre y email del cliente son obligatorios");
        }
    }
}