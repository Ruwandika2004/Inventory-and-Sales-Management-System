package com.supermarket.inventory.service;

import com.supermarket.inventory.entity.Order;
import com.supermarket.inventory.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesService {
    private final OrderRepository repository;

    public SalesService(OrderRepository repository) {
        this.repository = repository;
    }

    /** CREATE */
    public void processOrder(Order order) {
        order.calculateTotal(); // Polymorphism: applies tax or discount
        repository.save(order);
    }

    /** READ (all) */
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    /** READ (single) — used by edit form to pre-fill */
    public Order getOrderById(String id) {
        Optional<Order> order = repository.findById(id);
        return order.orElse(null);
    }

    /** UPDATE */
    public void updateOrder(Order order) {
        repository.save(order); // JPA save() acts as upsert
    }

    /** DELETE */
    public void deleteOrder(String id) {
        repository.deleteById(id);
    }
}
