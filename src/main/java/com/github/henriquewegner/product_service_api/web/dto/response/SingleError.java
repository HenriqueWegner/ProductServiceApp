package com.github.henriquewegner.product_service_api.web.dto.response;

public record SingleError(
        String field,
        String error) {
}
