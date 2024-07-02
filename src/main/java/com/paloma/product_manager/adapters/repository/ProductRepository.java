package com.paloma.product_manager.adapters.repository;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.domain.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);

    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:name IS NULL OR p.name = :name) " +
            "AND (:description IS NULL OR p.description LIKE %:description%) " +
            "AND (:price IS NULL OR p.price = :price)")
    List<ProductEntity> findByFilters(String name, String description, Double price);
}
