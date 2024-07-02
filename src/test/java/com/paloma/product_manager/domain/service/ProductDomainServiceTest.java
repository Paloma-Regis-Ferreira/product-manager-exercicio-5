package com.paloma.product_manager.domain.service;

import com.paloma.product_manager.adapters.repository.ProductRepository;
import com.paloma.product_manager.domain.builders.ProductBuilder;
import com.paloma.product_manager.domain.exception.ProductNotFoundException;
import com.paloma.product_manager.domain.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDomainServiceTest {

    @InjectMocks
    @Spy
    private ProductDomainService service;

    @Mock
    private ProductRepository repository;

    @Test
    public void shouldThrowExceptionWhenProductDoesNotExists(){
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ProductNotFoundException.class, () ->
                service.findById(1L));

        assertEquals("Product not found with id: 1", ex.getMessage());
    }

    @Test
    public void shouldReturnOptionalEmptyWhenProductWithNameDoesNotExists(){
        when(repository.findByName(any())).thenReturn(Optional.empty());

        Optional<ProductEntity> response = service.findByName("Any");

        assertNotNull(response);
        assertEquals(Optional.empty(), response);
        assertFalse(response.isPresent());
    }

    @Test
    public void shouldThrowExceptionWhenDeleteAProductDoesNotExists(){
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ProductNotFoundException.class, () ->
                service.delete(1L));

        assertEquals("Product not found with id: 1", ex.getMessage());
        verify(repository, times(0)).delete(any());
    }

    @Test
    public void shouldDeleteWithSuccessWhenProductExists(){
        when(repository.findById(any())).thenReturn(Optional.of(ProductEntity.builder().build()));

        service.delete(1L);

        verify(repository, times(1)).delete(any());
    }

    @Test
    public void shouldUpdateWithSuccessWhenProductExists(){
        when(repository.findById(any())).thenReturn(Optional.of(ProductBuilder.aProduct().entityNow()));
        when(repository.save(any())).thenReturn(ProductBuilder.aProduct().entityNow());

        ProductEntity response = service.update(1L, ProductBuilder.aProduct().entityNow());

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldNotUpdateWhenProductDoesNotExists(){
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(ProductNotFoundException.class, () ->
                service.update(1L, ProductBuilder.aProduct().entityNow()));

        assertEquals("Product not found with id: 1", ex.getMessage());
        verify(repository, times(0)).save(any());
    }
}