package com.gammatech.coffee.models;

/**
 * Enumeración que representa los diferentes estados posibles de un pedido.
 * 
 * Los estados disponibles son:
 * - PENDING: El pedido está pendiente de procesamiento
 * - IN_PREPARATION: El pedido está siendo preparado
 * - SHIPPED: El pedido ha sido enviado
 * - DELIVERED: El pedido ha sido entregado al cliente
 * - CANCELLED: El pedido ha sido cancelado
 */
public enum OrderStatus {
    PENDING,
    IN_PREPARATION, 
    SHIPPED,
    DELIVERED,
    CANCELLED
}