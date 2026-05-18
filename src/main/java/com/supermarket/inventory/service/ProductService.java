package com.supermarket.inventory.service;

import com.supermarket.inventory.entity.Product;
import com.supermarket.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Fetch all products from the data repository
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve a specific product by its unique ID
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    // Save or Update product details (Fixed Void to Product Conversion)
    public Product saveProduct(Product product) {
        // Since the repository's save method returns void, we call it directly
        productRepository.save(product);
        // Then we return the saved product object to satisfy the controller requirements
        return product;
    }

    // Delete a specific product from the data repository using its ID
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}