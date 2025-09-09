package com.github.henriquewegner.product_service_api.web.common.exceptions;

import lombok.Getter;

public class InvalidFieldException extends RuntimeException{

    @Getter
    private String field;

    public InvalidFieldException(String field, String mensagem){
        super(mensagem);
        this.field = field;
    }

}
