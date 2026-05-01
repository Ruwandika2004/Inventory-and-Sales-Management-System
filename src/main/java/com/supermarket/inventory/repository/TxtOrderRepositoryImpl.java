package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * File I/O implementation of OrderRepository.
 * Stores data in: src/main/resources/data/orders.txt
 *
 * File format (pipe-delimited):
 *   Retail|orderId|baseAmount|taxRate|orderDate|customerUsername
 *   Wholesale|orderId|baseAmount|discountRate|orderDate|customerUsername
 */
@Repository
public class TxtOrderRepositoryImpl implements OrderRepository {

    private static final String FILE_PATH = "src/main/resources/data/orders.txt";

    @Override
    public void save(Order order) {
        List<Order> orders = findAll();
        orders.removeIf(o -> o.getOrderId().equals(order.getOrderId()));
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDateTime.now());
        }
        orders.add(order);
        writeAll(orders);
    }

    @Override
    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                String type      = parts[0];
                String orderId   = parts[1];
                double baseAmt   = Double.parseDouble(parts[2]);
                double rate      = Double.parseDouble(parts[3]);
                LocalDateTime dt = parts.length > 4 && !parts[4].isEmpty()
                        ? LocalDateTime.parse(parts[4]) : LocalDateTime.now();
                String customer  = parts.length > 5 ? parts[5] : "";

                if ("Retail".equals(type)) {
                    RetailOrder ro = new RetailOrder(orderId, baseAmt);
                    ro.setTaxRate(rate);
                    ro.setOrderDate(dt);
                    ro.setCustomerUsername(customer);
                    list.add(ro);
                } else if ("Wholesale".equals(type)) {
                    WholesaleOrder wo = new WholesaleOrder(orderId, baseAmt);
                    wo.setDiscountRate(rate);
                    wo.setOrderDate(dt);
                    wo.setCustomerUsername(customer);
                    list.add(wo);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Returns only orders placed by a specific customer */
    public List<Order> findByCustomer(String customerUsername) {
        List<Order> all = findAll();
        all.removeIf(o -> !customerUsername.equals(o.getCustomerUsername()));
        return all;
    }

    @Override
    public Optional<Order> findById(String id) {
        return findAll().stream().filter(o -> o.getOrderId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(String id) {
        List<Order> orders = findAll();
        orders.removeIf(o -> o.getOrderId().equals(id));
        writeAll(orders);
    }

    private void writeAll(List<Order> orders) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Order o : orders) {
                String dt  = o.getOrderDate() != null ? o.getOrderDate().toString() : LocalDateTime.now().toString();
                String cus = o.getCustomerUsername() != null ? o.getCustomerUsername() : "";
                String line;
                if (o instanceof RetailOrder ro) {
                    line = "Retail|" + o.getOrderId() + "|" + o.getBaseAmount() + "|" + ro.getTaxRate() + "|" + dt + "|" + cus;
                } else if (o instanceof WholesaleOrder wo) {
                    line = "Wholesale|" + o.getOrderId() + "|" + o.getBaseAmount() + "|" + wo.getDiscountRate() + "|" + dt + "|" + cus;
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
