package com.paloma.product_manager.domain.builders;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.domain.model.ProductEntity;

public class ProductBuilder {

    private Long id;
    private String name;
    private String description;
    private Double price;

    private ProductBuilder(){
    }

    public static ProductBuilder aProduct(){
        ProductBuilder builder = new ProductBuilder();
        initializeDefaultData(builder);
        return builder;
    }

    private static void initializeDefaultData(ProductBuilder builder) {
        builder.id = 1L;
        builder.name = "Product name";
        builder.description = "Product description";
        builder.price = 10.0;
    }

    public ProductBuilder withId(Long param){
        this.id = param;
        return this;
    }

    public ProductBuilder withName(String param){
        this.name = param;
        return this;
    }

    public ProductBuilder withDescription(String param){
        this.description = param;
        return this;
    }

    public ProductBuilder withPrice(Double param){
        this.price = param;
        return this;
    }

    public ProductDTO dtoNow(){
        return new ProductDTO(id, name, description, price);
    }

    public ProductEntity entityNow(){
        return new ProductEntity(id, name, description, price);
    }
}
