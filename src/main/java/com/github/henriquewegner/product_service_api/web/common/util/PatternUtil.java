package com.github.henriquewegner.product_service_api.web.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatternUtil {

    public static final String PRODUCT_NAME = "^[A-Za-zÀ-ÿ0-9' -]{2,100}$";
    public static final String PRODUCT_DESCRIPTION = "^[A-Za-zÀ-ÿ0-9.,;:!?'\\\"()\\-\\/\\s]{5,500}$";
    public static final String SKU = "^[A-Z0-9_-]+$";
}