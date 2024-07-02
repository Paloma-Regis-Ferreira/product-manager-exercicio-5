package com.paloma.product_manager.adapters.mapper;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.domain.builders.ProductBuilder;
import com.paloma.product_manager.domain.model.ProductEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {

    @Test
    public void shouldReturnDtoSameEntity(){
        ProductEntity entity = ProductBuilder.aProduct().entityNow();
        ProductDTO dto = ProductMapper.convertEntityToDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getPrice(), dto.getPrice());
    }

    @Test
    public void shouldReturnEntitySameDto(){
        ProductDTO dto = ProductBuilder.aProduct().dtoNow();
        ProductEntity entity = ProductMapper.convertDtoToEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getPrice(), entity.getPrice());
    }
}