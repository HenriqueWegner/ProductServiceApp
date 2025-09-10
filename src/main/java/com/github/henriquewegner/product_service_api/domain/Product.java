package com.github.henriquewegner.product_service_api.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Product {
    private String sku;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private int stock;
    private int reserved;

}
