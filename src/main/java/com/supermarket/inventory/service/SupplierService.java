package com.supermarket.inventory.service;

import com.supermarket.inventory.entity.Supplier;
import com.supermarket.inventory.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public void addSupplier(Supplier supplier) {
        repository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return repository.findAll();
    }

    public void updateSupplier(Supplier supplier) {
        repository.save(supplier);
    }

    public void deleteSupplier(String id) {
        repository.deleteById(id);
    }
}
