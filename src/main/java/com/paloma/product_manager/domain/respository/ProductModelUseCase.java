package com.paloma.product_manager.domain.respository;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.domain.model.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductModelUseCase {

    ProductEntity findById(Long id);
    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findAll();
    ProductEntity create(ProductEntity dto);
    void delete(Long id);
    ProductEntity update(Long id, ProductEntity dto);
    List<ProductEntity> findByFilters(String name, String description, Double price);
}
