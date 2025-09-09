package com.github.henriquewegner.product_service_api.application;

import com.github.henriquewegner.product_service_api.domain.Product;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;
import com.github.henriquewegner.product_service_api.ports.in.ProductUseCase;
import com.github.henriquewegner.product_service_api.ports.out.ProductRepository;
import com.github.henriquewegner.product_service_api.web.common.exceptions.DuplicatedRegistryException;
import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;
import com.github.henriquewegner.product_service_api.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public String createProduct(ProductRequestDTO productRequestDTO) {

        Product product = productMapper.toDomain(productRequestDTO);
        checkIfProductExists(product);
        ProductEntity savedEntity = productRepository.save(product);

        return savedEntity.getSku();
    }

    private void checkIfProductExists(Product product) {
        Optional<ProductEntity> productEntity = productRepository.findById(product.getSku());
        if(productEntity.isPresent()){
            throw new DuplicatedRegistryException("This product has already been registered.");
        }
    }
}
