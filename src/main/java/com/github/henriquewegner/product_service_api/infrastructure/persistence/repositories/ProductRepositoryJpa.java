package com.github.henriquewegner.product_service_api.infrastructure.persistence.repositories;

import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, String> {
}
