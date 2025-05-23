package com.gammatech.coffee.exceptions;

public class CoffeeOrderDuplicatedException extends RuntimeException {
    public CoffeeOrderDuplicatedException(String message) {
        super(message);
    }
}
