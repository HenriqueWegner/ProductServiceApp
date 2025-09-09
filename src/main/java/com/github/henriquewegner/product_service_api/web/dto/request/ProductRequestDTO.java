package com.github.henriquewegner.product_service_api.web.dto.request;

import com.github.henriquewegner.product_service_api.web.common.util.MessageUtil;
import com.github.henriquewegner.product_service_api.web.common.util.PatternUtil;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "Obligatory field.")
        @NotNull(message = "Obligatory field.")
        @Pattern(regexp = PatternUtil.SKU, message = MessageUtil.INVALID_SKU)
        String sku,
        @NotNull(message = "Obligatory field.")
        @NotBlank(message = "Obligatory field.")
        @Pattern(regexp = PatternUtil.PRODUCT_NAME, message = MessageUtil.INVALID_NAME)
        String name,
        @NotNull(message = "Obligatory field.")
        @NotBlank(message = "Obligatory field.")
        @Pattern(regexp = PatternUtil.PRODUCT_DESCRIPTION, message = MessageUtil.INVALID_DESCRIPTION)
        String description,
        @NotNull(message = "Obligatory field.")
        @Positive(message = "unitPrice must be positive")
        BigDecimal unitPrice,
        @NotNull(message = "Obligatory field.")
        @Positive(message = "stock must be positive")
        int stock
) {
}
