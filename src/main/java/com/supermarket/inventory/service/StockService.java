package com.supermarket.inventory.service;

import com.supermarket.inventory.entity.StockTransaction;
import com.supermarket.inventory.repository.StockTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    private final StockTransactionRepository repository;

    public StockService(StockTransactionRepository repository) {
        this.repository = repository;
    }

    public void addTransaction(StockTransaction transaction) {
        repository.save(transaction);
    }

    public List<StockTransaction> getAllTransactions() {
        return repository.findAll();
    }

    public void updateTransaction(StockTransaction transaction) {
        repository.save(transaction);
    }

    public void deleteTransaction(String id) {
        repository.deleteById(id);
    }

    public int getCurrentStock(String productId) {
        List<StockTransaction> txs = repository.findByProductId(productId);
        int stockIn  = txs.stream().filter(t -> "IN".equals(t.getType())).mapToInt(StockTransaction::getQuantity).sum();
        int stockOut = txs.stream().filter(t -> "OUT".equals(t.getType())).mapToInt(StockTransaction::getQuantity).sum();
        return stockIn - stockOut;
    }
}
