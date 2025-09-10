package com.github.henriquewegner.product_service_api.ports.in;

import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.request.ReserveRestockRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.response.ProductResponseDTO;

import java.util.Optional;

public interface ProductUseCase {

    String createProduct(ProductRequestDTO productRequestDTO);

    Optional<ProductResponseDTO> findProduct(String id);

    void reserveProduct(ReserveRestockRequestDTO reserveProductsRequestDTO);

    void restockProduct(ReserveRestockRequestDTO restockProductsRequestDTO);
}
