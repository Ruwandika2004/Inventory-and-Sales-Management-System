package com.supermarket.inventory.controller;

import com.supermarket.inventory.entity.Electronics;
import com.supermarket.inventory.entity.PerishableProduct;
import com.supermarket.inventory.entity.Product;
import com.supermarket.inventory.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * Accepts a flat JSON: { id, name, price, type, expiryDate, warrantyMonths }
     * type = "Perishable" | "Electronics"
     */
    @PostMapping
    public void addProduct(@RequestBody Map<String, Object> body) {
        service.addProduct(buildProduct(body));
    }

    @GetMapping
    public List<Product> getProducts() {
        return service.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
    }

    @PostMapping("/update")
    public void updateProduct(@RequestBody Map<String, Object> body) {
        service.updateProduct(buildProduct(body));
    }

    private Product buildProduct(Map<String, Object> body) {
        String id    = (String) body.get("id");
        String name  = (String) body.get("name");
        double price = Double.parseDouble(body.get("price").toString());
        String type  = (String) body.getOrDefault("type", "Perishable");

        if ("Electronics".equals(type)) {
            int warranty = Integer.parseInt(body.getOrDefault("warrantyMonths", "12").toString());
            return new Electronics(id, name, price, warranty);
        } else {
            String expiry = (String) body.getOrDefault("expiryDate", "2027-01-01");
            return new PerishableProduct(id, name, price, expiry);
        }
    }
}
