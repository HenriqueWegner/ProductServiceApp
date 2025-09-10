package com.github.henriquewegner.product_service_api.web.controller;

import com.github.henriquewegner.product_service_api.ports.in.ProductUseCase;
import com.github.henriquewegner.product_service_api.web.common.exceptions.InvalidRequestTypeException;
import com.github.henriquewegner.product_service_api.web.dto.request.ProductRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.request.ProcessStockRequestDTO;
import com.github.henriquewegner.product_service_api.web.dto.response.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
@Tag(name = "Products")
public class ProductController implements GenericController{

    private final ProductUseCase productUseCase;

    @PostMapping
//    @PreAuthorize("hasRole(@environment.getProperty('security.roles.admin-access'))")
    @Operation(summary = "Save", description="Save new customer.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered with success."),
            @ApiResponse(responseCode = "422", description = "Validation error."),
    })
    public ResponseEntity<Void> save(@RequestBody @Valid ProductRequestDTO productRequestDTO){
        log.info("Creating new product named: {}", productRequestDTO.name());

        String productId = productUseCase.createProduct(productRequestDTO);
        URI location = gerarHeaderLocation(productId);

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Find Product", description="Find Product.")
    public ResponseEntity<ProductResponseDTO> findProduct(@PathVariable String id){

        return productUseCase.findProduct(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("/stock")
    public ResponseEntity<Void> updateStock(@RequestBody @Valid ProcessStockRequestDTO processStockRequestDTO) {

        switch (processStockRequestDTO.type()) {
            case RESERVE -> productUseCase.reserveProduct(processStockRequestDTO);
            case RESTOCK -> productUseCase.restockProduct(processStockRequestDTO);
            case REMOVE -> productUseCase.removeStock(processStockRequestDTO);
            default -> throw new InvalidRequestTypeException("Invalid operation type");
        }
        return ResponseEntity.ok().build();
    }
}
