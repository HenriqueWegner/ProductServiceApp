package com.github.henriquewegner.product_service_api.web.common.exceptions;

public class InvalidRequestTypeException extends RuntimeException {
    public InvalidRequestTypeException(String message) {
        super(message);
    }
}
