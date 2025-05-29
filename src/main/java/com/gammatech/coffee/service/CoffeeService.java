package com.gammatech.coffee.service;

import com.gammatech.coffee.repository.CoffeeRepository;
import com.gammatech.coffee.exceptions.ResourceAlreadyExistsException;
import com.gammatech.coffee.exceptions.ResourceNotFoundException;
import com.gammatech.coffee.models.Coffee;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Servicio que gestiona las operaciones relacionadas con los cafés.
 * Proporciona métodos para crear, leer, actualizar y eliminar cafés,
 * así como para realizar validaciones y manejar la paginación.
 */
@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    /**
     * Constructor del servicio de cafés.
     * @param coffeeRepository Repositorio de cafés a inyectar
     */
    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    /**
     * Obtiene todos los cafés del sistema.
     * @return Lista con todos los cafés
     */
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    /**
     * Obtiene una página de cafés según los parámetros especificados.
     * @param page Número de página (comenzando desde 0)
     * @param pageSize Tamaño de cada página
     * @return Página de cafés
     */
    public Page<Coffee> getAllPageable(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return  coffeeRepository.findAll(pageable);
    }

    /**
     * Busca un café por su ID.
     * @param coffeeId ID del café a buscar
     * @return El café encontrado
     * @throws ResourceNotFoundException si no se encuentra el café
     */
    public Coffee getCoffeeById(Long coffeeId) {
        return coffeeRepository.findById(coffeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Cafe  con el id '" + coffeeId + "' no encontrado"));
    }

    /**
     * Crea un nuevo café en el sistema.
     * @param coffeeRequest Datos del café a crear
     * @return El café creado
     * @throws ResourceAlreadyExistsException si ya existe un café con el mismo nombre
     * @throws IllegalArgumentException si los datos del café son inválidos
     */
    public Coffee createCoffee(Coffee coffeeRequest) {
        if (coffeeRepository.existsByName(coffeeRequest.getName())) {
            throw new ResourceAlreadyExistsException("Ya existe un café con el nombre: " + coffeeRequest.getName());
        }
        validateData(coffeeRequest);
        return coffeeRepository.save(coffeeRequest);
    }

    /**
     * Actualiza un café existente.
     * @param coffeeId ID del café a actualizar
     * @param coffeeRequest Nuevos datos del café
     * @return El café actualizado
     * @throws ResourceNotFoundException si no se encuentra el café
     * @throws ResourceAlreadyExistsException si ya existe otro café con el nuevo nombre
     * @throws IllegalArgumentException si los datos del café son inválidos
     */
    public Coffee updateCoffee(Long coffeeId, Coffee coffeeRequest) {
        Coffee existingCoffee = coffeeRepository.findById(coffeeId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId));

        validateData(coffeeRequest);

        if (!existingCoffee.getName().equals(coffeeRequest.getName()) && 
            coffeeRepository.existsByName(coffeeRequest.getName())) {
            throw new ResourceAlreadyExistsException("Ya existe un café con el nombre: " + coffeeRequest.getName());
        }
        existingCoffee.setName(coffeeRequest.getName());
        existingCoffee.setPrice(coffeeRequest.getPrice());
        existingCoffee.setImageUrl(coffeeRequest.getImageUrl());
        return coffeeRepository.save(existingCoffee);
    }

    /**
     * Actualiza la URL de la imagen de un café.
     * @param coffeeId ID del café a actualizar
     * @param imageUrl Nueva URL de la imagen
     * @return El café actualizado
     * @throws ResourceNotFoundException si no se encuentra el café
     * @throws IllegalArgumentException si la URL de la imagen es nula o vacía
     */
    public Coffee updateCoffeeImageUrl(Long coffeeId, String imageUrl) {
        Coffee existingCoffee = coffeeRepository.findById(coffeeId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId));

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva URL de la imagen no puede estar vacía");
        }
        existingCoffee.setImageUrl(imageUrl);
        return coffeeRepository.save(existingCoffee);
    }

    /**
     * Elimina un café del sistema.
     * @param coffeeId ID del café a eliminar
     * @throws ResourceNotFoundException si no se encuentra el café
     */
    public void deleteCoffee(Long coffeeId) {
        Coffee deleteCoffee = coffeeRepository.findById(coffeeId)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el café con ID: " + coffeeId));
        coffeeRepository.delete(deleteCoffee);
    }

    /**
     * Valida los datos de un café.
     * @param coffee Café a validar
     * @throws IllegalArgumentException si los datos del café son inválidos
     */
    private void validateData(Coffee coffee) {
        if (coffee.getName() == null || coffee.getName().trim().isEmpty() ||
                coffee.getImageUrl() == null || coffee.getImageUrl().trim().isEmpty() ||
                coffee.getPrice() <= 0) {
            throw new IllegalArgumentException("Datos inválidos");
        }
    }

}
