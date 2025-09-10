package com.github.henriquewegner.product_service_api.web.dto.request;

import com.github.henriquewegner.product_service_api.web.common.util.MessageUtil;
import com.github.henriquewegner.product_service_api.web.common.util.PatternUtil;
import jakarta.validation.constraints.*;

public record ReservedItemRequestDTO(
        @NotNull(message = "Obligatory field.")
        @Pattern(regexp = PatternUtil.SKU, message = MessageUtil.INVALID_SKU)
        String sku,
        @NotNull(message = "Obligatory field.")
        @Positive(message = "stock must be positive")
        int quantity
) {
}
