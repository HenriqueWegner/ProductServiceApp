package com.github.henriquewegner.product_service_api.web.common.exceptions;

public class ApiIntegrationException extends RuntimeException {
    public ApiIntegrationException(String message) {
        super(message);
    }
}
