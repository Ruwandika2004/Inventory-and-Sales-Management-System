package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
    Optional<Order> findById(String id);
    void deleteById(String id);
}
