package com.paloma.product_manager.adapters.controller;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.application.ProductServiceUseCase;
import com.paloma.product_manager.domain.builders.ProductBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServiceUseCase service;

    @InjectMocks
    private ProductController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetProductById() throws Exception {
        when(service.getProductById(1L)).thenReturn(ProductBuilder.aProduct().dtoNow());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Product name"))
                .andExpect(jsonPath("$.description").value("Product description"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(service, times(1)).getProductById(1L);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<ProductDTO> products = Arrays.asList(
                ProductBuilder.aProduct().dtoNow(),
                ProductBuilder.aProduct().withId(2L).dtoNow());

        when(service.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Product name"))
                .andExpect(jsonPath("$[0].description").value("Product description"))
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Product name"))
                .andExpect(jsonPath("$[1].description").value("Product description"))
                .andExpect(jsonPath("$[1].price").value(10.0));

        verify(service, times(1)).getAllProducts();
    }

    @Test
    public void testCreateProduct() throws Exception {
        when(service.createProduct(any(ProductDTO.class))).thenReturn(ProductBuilder.aProduct().dtoNow());

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content("{\"name\": \"Product name\", \"description\": \"Product description\", \"price\": 10.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Product name"))
                .andExpect(jsonPath("$.description").value("Product description"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(service, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        when(service.updateProduct(anyLong(), any(ProductDTO.class)))
                .thenReturn(ProductBuilder.aProduct().dtoNow());

        mockMvc.perform(put("/api/products/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Product name\", \"description\": \"Product description\", \"price\": 10.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Product name"))
                .andExpect(jsonPath("$.description").value("Product description"))
                .andExpect(jsonPath("$.price").value(10.0));

        verify(service, times(1)).updateProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        doNothing().when(service).deleteProduct(anyLong());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteProduct(anyLong());
    }
}