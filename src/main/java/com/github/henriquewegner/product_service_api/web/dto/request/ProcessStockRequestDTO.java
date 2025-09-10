package com.github.henriquewegner.product_service_api.web.dto.request;

import com.github.henriquewegner.product_service_api.domain.enums.RequestType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProcessStockRequestDTO(
        @NotNull(message = "Obligatory field.")
        RequestType type,
        List<ReservedItemRequestDTO> items
){
}
