package com.gammatech.coffee.repository;
import com.gammatech.coffee.models.Coffee;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Coffee.
 * 
 * Esta interfaz proporciona operaciones de acceso a datos para la entidad Coffee,
 * extendiendo JpaRepository que ya incluye métodos CRUD básicos y paginación.
 * 
 * La anotación @Repository indica que esta interfaz es un componente de persistencia
 * que maneja operaciones de acceso a datos.
 * 
 * @see Coffee
 * @see JpaRepository
 */
@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    
    /**
     * Verifica si existe un café con el nombre especificado.
     * @param name El nombre del café a buscar
     * @return true si existe un café con ese nombre, false en caso contrario
     */
    boolean existsByName(String name);

}