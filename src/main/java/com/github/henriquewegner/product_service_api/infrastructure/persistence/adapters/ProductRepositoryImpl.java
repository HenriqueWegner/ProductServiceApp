package com.github.henriquewegner.product_service_api.infrastructure.persistence.adapters;

import com.github.henriquewegner.product_service_api.domain.model.Product;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.repositories.ProductRepositoryJpa;
import com.github.henriquewegner.product_service_api.ports.out.ProductRepository;
import com.github.henriquewegner.product_service_api.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductRepositoryJpa productRepositoryJpa;
    private final ProductMapper productMapper;

    @Override
    public Optional<ProductEntity> findById(String id) {
        return productRepositoryJpa.findById(id);
    }

    @Override
    public ProductEntity save(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        return productRepositoryJpa.save(productEntity);
    }
}
