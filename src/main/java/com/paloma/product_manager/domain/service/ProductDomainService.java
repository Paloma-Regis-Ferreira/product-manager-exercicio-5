package com.paloma.product_manager.domain.service;

import com.paloma.product_manager.adapters.dto.ProductDTO;
import com.paloma.product_manager.adapters.repository.ProductRepository;
import com.paloma.product_manager.domain.exception.ProductNotFoundException;
import com.paloma.product_manager.domain.model.ProductEntity;
import com.paloma.product_manager.domain.respository.ProductModelUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductDomainService implements ProductModelUseCase{

    @Autowired
    private ProductRepository repository;


    @Override
    public ProductEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(id));
    }

    @Override
    public Optional<ProductEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<ProductEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public ProductEntity create(ProductEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        ProductEntity entity = findById(id);
        repository.delete(entity);
    }

    @Override
    public ProductEntity update(Long id, ProductEntity dto) {
        ProductEntity entity = findById(id);

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        return repository.save(entity);
    }

    @Override
    public List<ProductEntity> findByFilters(String name, String description, Double price) {
        return repository.findByFilters(name, description, price);
    }
}
