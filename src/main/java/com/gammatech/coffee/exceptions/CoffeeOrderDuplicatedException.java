package com.gammatech.coffee.exceptions;

/**
 * Excepción que se lanza cuando se intenta crear o actualizar un pedido
 * que contiene cafés duplicados.
 * 
 * Esta excepción extiende de RuntimeException y se utiliza para manejar
 * casos donde un pedido contiene el mismo tipo de café más de una vez,
 * lo cual no está permitido en el sistema.
 */
public class CoffeeOrderDuplicatedException extends RuntimeException {
    
    /**
     * Constructor que crea una nueva instancia de CoffeeOrderDuplicatedException.
     * 
     * @param message El mensaje que describe la razón de la excepción
     */
    public CoffeeOrderDuplicatedException(String message) {
        super(message);
    }
}
