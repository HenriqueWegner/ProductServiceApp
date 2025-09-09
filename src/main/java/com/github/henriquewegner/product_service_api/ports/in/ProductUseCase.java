package com.github.henriquewegner.product_service_api.ports.in;

import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;

public interface ProductUseCase {

    String createProduct(ProductRequestDTO productRequestDTO);
}
