package com.gammatech.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gammatech.coffee.models.Order;

/**
 * Repositorio para la entidad Order.
 * 
 * Esta interfaz proporciona operaciones de acceso a datos para la entidad Order,
 * extendiendo JpaRepository que ya incluye métodos CRUD básicos y paginación.
 * 
 * La anotación @Repository indica que esta interfaz es un componente de persistencia
 * que maneja operaciones de acceso a datos.
 * 
 * @see Order
 * @see JpaRepository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
   
}
