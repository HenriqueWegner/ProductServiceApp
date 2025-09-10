package com.github.henriquewegner.product_service_api.web.dto.request;

public record ReservedItemRequestDTO(
        String sku,
        int quantity
) {
}
