package com.github.henriquewegner.product_service_api.web.common.exceptions;

public class DuplicatedRegistryException extends RuntimeException {
    public DuplicatedRegistryException(String message) {
        super(message);
    }
}
