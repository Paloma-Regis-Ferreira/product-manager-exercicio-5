package com.paloma.product_manager.application;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.adapters.mapper.ProductMapper;
import com.paloma.product_manager.application.messaging.CreateProductProducer;
import com.paloma.product_manager.domain.exception.ProductAlreadyExistsException;
import com.paloma.product_manager.domain.model.ProductEntity;
import com.paloma.product_manager.domain.respository.ProductModelUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.paloma.product_manager.adapters.mapper.ProductMapper.convertDtoToEntity;
import static com.paloma.product_manager.adapters.mapper.ProductMapper.convertEntityToDto;

@Service
public class ProductService implements ProductServiceUseCase {

    @Autowired
    private ProductModelUseCase useCase;

    @Autowired
    private CreateProductProducer productProducer;

    @Override
    public ProductDTO getProductById(Long id) {
        ProductEntity entity = useCase.findById(id);
        return convertEntityToDto(entity);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return useCase.findAll().stream()
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductDTO newProduct) {
        String treatedProductName = treatmentString(newProduct.getName());

        Optional<ProductEntity> entity = useCase.findByName(treatedProductName);
        if (entity.isPresent()){
            throw new ProductAlreadyExistsException(entity.get().getId(), convertEntityToDto(entity.get()));
        }
        newProduct.setName(treatedProductName);

        ProductEntity productCreated = useCase.create(convertDtoToEntity(newProduct));
        productProducer.publishProductMessage(productCreated);

        return convertEntityToDto(productCreated);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO updateProduct) {
        String treatedProductName = treatmentString(updateProduct.getName());
        updateProduct.setName(treatedProductName);
        return convertEntityToDto(useCase.update(
                id, convertDtoToEntity(updateProduct)));
    }

    @Override
    public void deleteProduct(Long id) {
        useCase.delete(id);
    }

    @Override
    public List<ProductDTO> findyProductsByFilters(String name, String description, Double price) {
        return useCase.findByFilters(name, description, price).stream()
                .map(ProductMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    protected String treatmentString(String productName){
        String normalized = Normalizer.normalize(productName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String treatedName = pattern.matcher(normalized).replaceAll("");
        treatedName = treatedName.replaceAll("[^a-zA-Z0-9]", " ");
        treatedName = treatedName.replaceAll("\\s+", " ");
        treatedName = treatedName.trim();
        return treatedName;
    }
}
