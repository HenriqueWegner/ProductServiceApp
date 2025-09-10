package com.github.henriquewegner.product_service_api.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private String sku;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private int stock;
    private int reserved;

}
