package com.github.henriquewegner.product_service_api.web.dto.request;

import java.util.List;

public record ReserveRestockRequestDTO(
        List<ReservedItemRequestDTO> items
){
}
