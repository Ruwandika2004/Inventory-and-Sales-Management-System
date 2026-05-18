package com.supermarket.inventory.controller;

import com.supermarket.inventory.entity.Supplier;
import com.supermarket.inventory.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @PostMapping
    public void addSupplier(@RequestBody Supplier supplier) {
        service.addSupplier(supplier);
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return service.getAllSuppliers();
    }

    @PostMapping("/update")
    public void updateSupplier(@RequestBody Supplier supplier) {
        service.updateSupplier(supplier);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable String id) {
        service.deleteSupplier(id);
    }
}
