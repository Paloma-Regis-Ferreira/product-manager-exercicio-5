package com.paloma.product_manager.exception;

import com.paloma.product_manager.adapters.controller.ProductController;
import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.application.ProductServiceUseCase;
import com.paloma.product_manager.domain.builders.ProductBuilder;
import com.paloma.product_manager.domain.exception.ProductAlreadyExistsException;
import com.paloma.product_manager.domain.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@WebMvcTest(controllers = {ProductController.class, ControllerAdviceHandler.class})
public class ControllerAdviceHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceUseCase service;

    @Test
    public void testHandleProductNotFoundException() throws Exception {
        when(service.getProductById(1L)).thenThrow(new ProductNotFoundException(1L));

        mockMvc.perform(get("/api/products/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found with id: 1"));
    }

    @Test
    public void testHandleProductAlreadyExistsException() throws Exception {
        when(service.createProduct(any(ProductDTO.class))).thenThrow(
                new ProductAlreadyExistsException(1L, ProductBuilder.aProduct().dtoNow()));

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Product name\", \"description\": \"Product description\", \"price\": 10.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Product already exists with id: 1"))
                .andExpect(jsonPath("$.product.name").value("Product name"))
                .andExpect(jsonPath("$.product.description").value("Product description"))
                .andExpect(jsonPath("$.product.price").value(10.0));
    }
}