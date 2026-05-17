package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

/**
 * File I/O implementation of ProductRepository.
 * Stores data in: src/main/resources/data/products.txt
 *
 * File format (pipe-delimited):
 *   Perishable|id|name|price|expiryDate
 *   Electronics|id|name|price|warrantyMonths
 */
@Repository
public class TxtProductRepositoryImpl implements ProductRepository {

    private static final String FILE_PATH = "src/main/resources/data/products.txt";

    @Override
    public void save(Product product) {
        List<Product> products = findAll();
        // Remove existing entry with same ID (update behaviour)
        products.removeIf(p -> p.getId().equals(product.getId()));
        products.add(product);
        writeAll(products);
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                String type  = parts[0];
                String id    = parts[1];
                String name  = parts[2];
                double price = Double.parseDouble(parts[3]);

                if ("Perishable".equals(type)) {
                    String expiry = parts.length > 4 ? parts[4] : "";
                    list.add(new PerishableProduct(id, name, price, expiry));
                } else if ("Electronics".equals(type)) {
                    int warranty = parts.length > 4 && !parts[4].isEmpty() ? Integer.parseInt(parts[4]) : 0;
                    list.add(new Electronics(id, name, price, warranty));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Product> findById(String id) {
        return findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(String id) {
        List<Product> products = findAll();
        products.removeIf(p -> p.getId().equals(id));
        writeAll(products);
    }

    private void writeAll(List<Product> products) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Product p : products) {
                String line;
                if (p instanceof PerishableProduct pp) {
                    line = "Perishable|" + p.getId() + "|" + p.getName() + "|" + p.getPrice() + "|" + pp.getExpiryDate();
                } else if (p instanceof Electronics e) {
                    line = "Electronics|" + p.getId() + "|" + p.getName() + "|" + p.getPrice() + "|" + e.getWarrantyMonths();
                } else {
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
