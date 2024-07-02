package com.paloma.product_manager.application;

import com.paloma.product_manager.adapters.dto.ProductDTO;

import java.util.List;

public interface ProductServiceUseCase {

    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductDTO newProduct);
    ProductDTO updateProduct(Long id, ProductDTO updateProduct);
    void deleteProduct(Long id);
    List<ProductDTO> findyProductsByFilters(String name, String description, Double price);
}
