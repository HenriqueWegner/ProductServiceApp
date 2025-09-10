package com.github.henriquewegner.product_service_api.web.mapper;

import com.github.henriquewegner.product_service_api.domain.model.Product;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;
import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.response.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "unitPrice", target = "unitPrice")
    Product toDomain(ProductRequestDTO dto);
    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product product);

    ProductResponseDTO toDTO(ProductEntity entity);
}
