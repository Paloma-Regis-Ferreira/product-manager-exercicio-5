package com.paloma.product_manager.application;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.application.messaging.CreateProductProducer;
import com.paloma.product_manager.domain.builders.ProductBuilder;
import com.paloma.product_manager.domain.exception.ProductAlreadyExistsException;
import com.paloma.product_manager.domain.model.ProductEntity;
import com.paloma.product_manager.domain.respository.ProductModelUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    @Spy
    private ProductService service;

    @Mock
    private ProductModelUseCase useCase;

    @Mock
    private CreateProductProducer productProducer;

    
    @Test
    public void shouldReturnDtoWhenEntityIdExists(){
        when(useCase.findById(any())).thenReturn(ProductBuilder.aProduct().entityNow());

        ProductDTO response = service.getProductById(1L);

        assertNotNull(response);
        assertInstanceOf(ProductDTO.class, response);
        assertEquals(1L, response.getId());
    }

    @Test
    public void shouldReturnDtoListWhenCallGetAllProducts(){
        when(useCase.findAll()).thenReturn(List.of(ProductBuilder.aProduct().entityNow()));

        List<ProductDTO> response = service.getAllProducts();

        assertNotNull(response);
        assertTrue(response.stream().allMatch(item -> item instanceof ProductDTO));
    }

    @Test
    public void shouldReturnDtoListWhenCallGetAllProductsAndEntityDescriptionIsNull(){
        when(useCase.findAll()).thenReturn(List.of(ProductBuilder.aProduct().withDescription(null).entityNow()));

        List<ProductDTO> response = service.getAllProducts();

        assertNotNull(response);
        assertTrue(response.stream().allMatch(item -> item instanceof ProductDTO));
    }

    @Test
    public void shouldCreateProductWithSuccessWhenProductNameDoesNotExistInDatabase(){
        when(useCase.findByName(any())).thenReturn(Optional.empty());
        when(useCase.create(any(ProductEntity.class))).thenReturn(ProductBuilder.aProduct().entityNow());

        ProductDTO response = service.createProduct(ProductBuilder.aProduct().withId(null).dtoNow());

        assertNotNull(response);
        assertInstanceOf(ProductDTO.class, response);
        verify(useCase, times(1)).create(any());
    }

    @Test
    public void shouldNotCreateProductWhenProductNameAlreadyExistsInDatabase(){
        when(useCase.findByName(any())).thenReturn(Optional.of(ProductBuilder.aProduct().entityNow()));

        Exception ex = assertThrows(ProductAlreadyExistsException.class, () ->
                service.createProduct(ProductBuilder.aProduct().dtoNow()));

        assertEquals("Product already exists with id: 1", ex.getMessage());
        verify(useCase, times(0)).create(any());
    }

    @Test
    public void shouldCreateProductWhitTreatedName(){
        when(useCase.findByName(any())).thenReturn(Optional.empty());
        when(useCase.create(any(ProductEntity.class))).thenReturn(ProductBuilder.aProduct().entityNow());

        ProductDTO response = service.createProduct(ProductBuilder.aProduct().withId(null).withName(" Prõdúçt-nãme ").dtoNow());

        assertEquals("Product name", response.getName());
        verify(useCase, times(1)).create(any());
    }

    @Test
    public void shouldUpdateProductWithSuccessAndFinalNameTreated(){
        when(useCase.update(any(), any())).thenReturn(ProductBuilder.aProduct().entityNow());
        ProductDTO dtoToUpdate = ProductBuilder.aProduct().withId(null).withName(" Prõdúçt-nãme ").dtoNow();

        ProductDTO response = service.updateProduct(1L, dtoToUpdate);

        assertEquals("Product name", response.getName());
        verify(service, times(1)).treatmentString(" Prõdúçt-nãme ");
        verify(useCase, times(1)).update(any(), any());
    }

    @Test
    public void shouldDeleteWithSuccess(){
        service.deleteProduct(1L);

        verify(useCase, times(1)).delete(any());
    }
}