package com.github.henriquewegner.product_service_api.web.mapper;

import com.github.henriquewegner.product_service_api.domain.Product;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;
import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "unitPrice", target = "unitPrice")
    Product toDomain(ProductRequestDTO dto);

    ProductEntity toEntity(Product product);
}
