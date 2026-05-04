package com.supermarket.inventory.service;

import com.supermarket.inventory.entity.Product;
import com.supermarket.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void addProduct(Product product) {
        repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void updateProduct(Product product) {
        repository.save(product); // save() acts as upsert in JPA
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
}
