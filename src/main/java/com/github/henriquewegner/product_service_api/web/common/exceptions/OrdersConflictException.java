package com.github.henriquewegner.product_service_api.web.common.exceptions;

public class OrdersConflictException extends RuntimeException {
    public OrdersConflictException(String message) {
        super(message);
    }
}
