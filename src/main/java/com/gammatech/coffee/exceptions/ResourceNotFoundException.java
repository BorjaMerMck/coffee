package com.gammatech.coffee.exceptions;

/**
 * Excepción personalizada para manejar casos donde un recurso solicitado no es encontrado.
 * 
 * Esta excepción extiende de RuntimeException, lo que significa que es una excepción no verificada
 * que puede ser lanzada durante la ejecución normal del programa cuando se intenta acceder
 * a un recurso que no existe en el sistema.
 * 
 * @author Borja Merchan mckenna
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructor que acepta un mensaje descriptivo del error.
     * 
     * @param message El mensaje que describe por qué el recurso no fue encontrado
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}