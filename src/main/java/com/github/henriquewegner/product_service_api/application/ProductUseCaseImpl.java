package com.github.henriquewegner.product_service_api.application;

import com.github.henriquewegner.product_service_api.domain.model.Product;
import com.github.henriquewegner.product_service_api.infrastructure.persistence.entities.ProductEntity;
import com.github.henriquewegner.product_service_api.ports.in.ProductUseCase;
import com.github.henriquewegner.product_service_api.ports.out.ProductRepository;
import com.github.henriquewegner.product_service_api.web.common.exceptions.DuplicatedRegistryException;
import com.github.henriquewegner.product_service_api.web.common.exceptions.InsufficientStockException;
import com.github.henriquewegner.product_service_api.web.common.exceptions.ProductException;
import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.request.ProcessStockRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.response.ProductResponseDTO;
import com.github.henriquewegner.product_service_api.web.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

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
    public void reserveProduct(ProcessStockRequestDTO requestDTO) {
        processStockOperation(
                requestDTO,
                this::reserveStock,
                (product, qty) -> qty <= product.getStock()
        );
    }

    @Override
    @Transactional
    public void restockProduct(ProcessStockRequestDTO requestDTO) {
        processStockOperation(
                requestDTO,
                this::restock,
                (product, qty) -> qty <= product.getReserved()
        );
    }

    @Override
    @Transactional
    public void removeStock(ProcessStockRequestDTO requestDTO) {
        processStockOperation(
                requestDTO,
                this::removeFromReserve,
                (product, qty) -> qty <= product.getReserved()
        );
    }

    private void processStockOperation(
            ProcessStockRequestDTO requestDTO,
            BiConsumer<ProductEntity, Integer> operation,
            BiPredicate<ProductEntity, Integer> stockCheck) {

        checkIfProductsExist(requestDTO);

        requestDTO.items().forEach(item ->
                productRepository.findById(item.sku().toUpperCase())
                        .ifPresent(productEntity -> {
                            if (!stockCheck.test(productEntity, item.quantity())) {
                                throw new InsufficientStockException("There's not enough stock.");
                            }
                            operation.accept(productEntity, item.quantity());
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

    private void checkIfProductsExist(ProcessStockRequestDTO processStockRequestDTO){
        boolean allExist = processStockRequestDTO.items().stream()
                .allMatch(item -> productRepository.findById(item.sku().toUpperCase()).isPresent());

        if (!allExist) {
            throw new ProductException("Some of the products do not exist.");
        }
    }


    private void reserveStock(ProductEntity productEntity, int quantity) {
        productEntity.setReserved(productEntity.getReserved() + quantity);
        productEntity.setStock(productEntity.getStock() - quantity);
    }

    private void restock(ProductEntity productEntity, int quantity) {
        productEntity.setStock(productEntity.getStock() + quantity);
        productEntity.setReserved(productEntity.getReserved() - quantity);
    }

    private void removeFromReserve(ProductEntity productEntity, int quantity) {
        productEntity.setReserved(productEntity.getReserved() - quantity);
    }
}
