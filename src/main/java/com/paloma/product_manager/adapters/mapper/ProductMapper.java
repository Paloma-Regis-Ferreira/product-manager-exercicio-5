package com.paloma.product_manager.adapters.mapper;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.domain.model.ProductEntity;

public class ProductMapper {

    public static ProductDTO convertEntityToDto(ProductEntity entity){
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .build();
    }

    public static ProductEntity convertDtoToEntity(ProductDTO dto){
        return ProductEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }
}
