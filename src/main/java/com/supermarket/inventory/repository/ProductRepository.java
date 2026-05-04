package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    List<Product> findAll();
    Optional<Product> findById(String id);
    void deleteById(String id);
}
