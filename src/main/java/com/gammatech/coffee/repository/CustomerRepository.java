package com.gammatech.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gammatech.coffee.models.Customer;

/**
 * Repositorio para la entidad Customer.
 * 
 * Esta interfaz proporciona operaciones de acceso a datos para la entidad Customer,
 * extendiendo JpaRepository que ya incluye métodos CRUD básicos y paginación.
 * 
 * La anotación @Repository indica que esta interfaz es un componente de persistencia
 * que maneja operaciones de acceso a datos.
 * 
 * @see Customer
 * @see JpaRepository
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  /**
   * Verifica si existe un cliente con el email especificado.
   * @param email El email a buscar
   * @return true si existe un cliente con ese email, false en caso contrario
   */
  boolean existsByEmail(String email);

  /**
   * Busca un cliente por su email.
   * @param email El email del cliente a buscar
   * @return El cliente encontrado o null si no existe
   */
  Customer findByEmail(String email);
}
