package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.StockTransaction;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * File I/O implementation of StockTransactionRepository.
 * Stores data in: src/main/resources/data/stock.txt
 *
 * File format (pipe-delimited):
 *   transactionId|productId|quantity|type|timestamp
 */
@Repository
public class TxtStockTransactionRepositoryImpl implements StockTransactionRepository {

    private static final String FILE_PATH = "src/main/resources/data/stock.txt";

    @Override
    public void save(StockTransaction transaction) {
        List<StockTransaction> all = findAll();
        all.removeIf(t -> t.getTransactionId().equals(transaction.getTransactionId()));
        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(LocalDateTime.now());
        }
        all.add(transaction);
        writeAll(all);
    }

    @Override
    public List<StockTransaction> findAll() {
        List<StockTransaction> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                StockTransaction t = new StockTransaction();
                t.setTransactionId(parts[0]);
                t.setProductId(parts[1]);
                t.setQuantity(Integer.parseInt(parts[2]));
                t.setType(parts[3]);
                t.setTimestamp(parts.length > 4 && !parts[4].isEmpty()
                        ? LocalDateTime.parse(parts[4])
                        : LocalDateTime.now());
                list.add(t);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<StockTransaction> findById(String id) {
        return findAll().stream().filter(t -> t.getTransactionId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(String id) {
        List<StockTransaction> all = findAll();
        all.removeIf(t -> t.getTransactionId().equals(id));
        writeAll(all);
    }

    @Override
    public List<StockTransaction> findByProductId(String productId) {
        return findAll().stream()
                .filter(t -> t.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    private void writeAll(List<StockTransaction> transactions) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (StockTransaction t : transactions) {
                String ts = t.getTimestamp() != null ? t.getTimestamp().toString() : LocalDateTime.now().toString();
                writer.write(t.getTransactionId() + "|" + t.getProductId() + "|"
                        + t.getQuantity() + "|" + t.getType() + "|" + ts);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
