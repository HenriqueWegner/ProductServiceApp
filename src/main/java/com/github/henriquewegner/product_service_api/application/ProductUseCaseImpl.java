package com.github.henriquewegner.product_service_api.application;

import com.github.henriquewegner.product_service_api.domain.Product;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;
import com.github.henriquewegner.product_service_api.ports.in.ProductUseCase;
import com.github.henriquewegner.product_service_api.ports.out.ProductRepository;
import com.github.henriquewegner.product_service_api.web.common.exceptions.DuplicatedRegistryException;
import com.github.henriquewegner.product_service_api.web.common.exceptions.InsufficientStockException;
import com.github.henriquewegner.product_service_api.web.common.exceptions.ProductException;
import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.request.ReserveRestockRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.response.ProductResponseDTO;
import com.github.henriquewegner.product_service_api.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public String createProduct(ProductRequestDTO productRequestDTO) {

        Product product = productMapper.toDomain(productRequestDTO);
        checkIfProductIsRegistered(product);
        ProductEntity savedEntity = productRepository.save(product);

        return savedEntity.getSku();
    }

    @Override
    public Optional<ProductResponseDTO> findProduct(String id) {
        return productRepository.findById(id.toUpperCase()).map(productMapper::toDTO);
    }

    @Override
    @Transactional
    public void reserveProduct(ReserveRestockRequestDTO reserveProductsRequestDTO) {
        checkIfProductsExist(reserveProductsRequestDTO);

        reserveProductsRequestDTO.items().forEach(reservedItemRequestDTO ->
                productRepository
                        .findById(reservedItemRequestDTO.sku().toUpperCase())
                        .ifPresent(productEntity -> {
                            checkStock(productEntity.getStock(), reservedItemRequestDTO.quantity());
                            reserveStock(productEntity, reservedItemRequestDTO.quantity());
                            productRepository.save(productMapper.toDomain(productEntity));
                        }));
    }

    @Override
    @Transactional
    public void restockProduct(ReserveRestockRequestDTO restockProductsRequestDTO) {
        checkIfProductsExist(restockProductsRequestDTO);

        restockProductsRequestDTO.items().forEach(restockProductRequestDTO ->
                productRepository
                        .findById(restockProductRequestDTO.sku().toUpperCase())
                        .ifPresent(productEntity -> {
                            checkStock(productEntity.getReserved(), restockProductRequestDTO.quantity());
                            restock(productEntity, restockProductRequestDTO.quantity());
                            productRepository.save(productMapper.toDomain(productEntity));
                        })
        );
    }

    private void checkIfProductIsRegistered(Product product) {
        Optional<ProductEntity> productEntity = productRepository.findById(product.getSku());
        if(productEntity.isPresent()){
            throw new DuplicatedRegistryException("This product has already been registered.");
        }
    }

    private void checkIfProductsExist(ReserveRestockRequestDTO reserveRestockRequestDTO){
        boolean allExist = reserveRestockRequestDTO.items().stream()
                .allMatch(item -> productRepository.findById(item.sku().toUpperCase()).isPresent());

        if (!allExist) {
            throw new ProductException("Some of the products do not exist.");
        }
    }

    private void checkStock(int stock, int reservedQuantity) {
        if(reservedQuantity > stock){
            throw new InsufficientStockException("There's not enough stock.");
        }
    }

    private ProductEntity reserveStock(ProductEntity productEntity, int quantity) {
        productEntity.setReserved(productEntity.getReserved() + quantity);
        productEntity.setStock(productEntity.getStock() - quantity);
        return productEntity;
    }

    private ProductEntity restock(ProductEntity productEntity, int quantity) {
        productEntity.setStock(productEntity.getStock() + quantity);
        productEntity.setReserved(productEntity.getReserved() - quantity);
        return productEntity;
    }
}
