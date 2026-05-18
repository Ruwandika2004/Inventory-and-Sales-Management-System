package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.Supplier;
import java.util.List;
import java.util.Optional;

public interface SupplierRepository {
    void save(Supplier supplier);
    List<Supplier> findAll();
    Optional<Supplier> findById(String id);
    void deleteById(String id);
}
