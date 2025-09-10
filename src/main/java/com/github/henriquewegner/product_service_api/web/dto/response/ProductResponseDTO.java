package com.github.henriquewegner.product_service_api.web.dto.response;

import java.math.BigDecimal;

public record ProductResponseDTO(
        String sku,
        String name,
        String description,
        BigDecimal unitPrice,
        int stock,
        int reserved
) {
}
