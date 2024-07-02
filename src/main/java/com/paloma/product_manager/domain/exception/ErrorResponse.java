package com.paloma.product_manager.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paloma.product_manager.adapters.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String message;
    private ProductDTO product;

    public ErrorResponse(String message, ProductDTO product) {
        this.message = message;
        this.product = product;
    }
}
