package com.gammatech.coffee.exceptions;

/**
 * Excepción que se lanza cuando se intenta crear o actualizar un recurso
 * que ya existe en el sistema.
 * 
 * Esta excepción extiende de RuntimeException y se utiliza para manejar
 * casos donde se intenta crear un recurso con datos únicos que ya están
 * siendo utilizados por otro recurso existente (por ejemplo, un email
 * que ya está registrado para otro cliente).
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    
    /**
     * Constructor que crea una nueva instancia de ResourceAlreadyExistsException.
     * 
     * @param message El mensaje que describe la razón de la excepción
     */
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
