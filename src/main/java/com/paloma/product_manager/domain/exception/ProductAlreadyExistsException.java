package com.paloma.product_manager.domain.exception;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class ProductAlreadyExistsException extends RuntimeException {

    private ProductDTO product;

    public ProductAlreadyExistsException(Long id, ProductDTO product) {
        super("Product already exists with id: " + id);
        this.product = product;
    }
}
