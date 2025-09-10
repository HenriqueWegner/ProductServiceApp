package com.github.henriquewegner.product_service_api.ports.out;

import com.github.henriquewegner.product_service_api.domain.model.Product;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;

import java.util.Optional;

public interface ProductRepository {

    Optional<ProductEntity> findById(String id);
    ProductEntity save(Product customer);
}
