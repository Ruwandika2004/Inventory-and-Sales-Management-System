package com.supermarket.inventory.controller;

import com.supermarket.inventory.entity.StockTransaction;
import com.supermarket.inventory.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @PostMapping
    public void addTransaction(@RequestBody StockTransaction transaction) {
        service.addTransaction(transaction);
    }

    @GetMapping
    public List<StockTransaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @PostMapping("/update")
    public void updateTransaction(@RequestBody StockTransaction transaction) {
        service.updateTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable String id) {
        service.deleteTransaction(id);
    }

    @GetMapping("/current/{productId}")
    public Map<String, Integer> getCurrentStock(@PathVariable String productId) {
        return Map.of("stock", service.getCurrentStock(productId));
    }
}
