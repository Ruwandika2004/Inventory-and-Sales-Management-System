package com.supermarket.inventory.controller;

import com.supermarket.inventory.entity.Order;
import com.supermarket.inventory.entity.RetailOrder;
import com.supermarket.inventory.entity.WholesaleOrder;
import com.supermarket.inventory.entity.Invoice;
import com.supermarket.inventory.repository.TxtOrderRepositoryImpl;
import com.supermarket.inventory.service.SalesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    private final SalesService service;
    private final TxtOrderRepositoryImpl orderRepo;

    public SalesController(SalesService service, TxtOrderRepositoryImpl orderRepo) {
        this.service   = service;
        this.orderRepo = orderRepo;
    }

    /**
     * CREATE — POST /api/sales
     * Accepts: { orderId, baseAmount, orderType, customerUsername (optional) }
     * Returns the generated Invoice.
     */
    @PostMapping
    public Invoice processOrder(@RequestBody Map<String, Object> payload) {
        String orderId          = (String) payload.get("orderId");
        double baseAmount       = Double.parseDouble(payload.get("baseAmount").toString());
        String orderType        = payload.getOrDefault("orderType", "Retail").toString();
        String customerUsername = payload.getOrDefault("customerUsername", "").toString();

        Order order;
        if ("Wholesale".equals(orderType)) {
            order = new WholesaleOrder(orderId, baseAmount);
        } else {
            order = new RetailOrder(orderId, baseAmount);
        }
        order.setCustomerUsername(customerUsername);

        service.processOrder(order);

        // Return Invoice as confirmation (OOP — Invoice wraps the Order)
        return new Invoice(UUID.randomUUID().toString(), order);
    }

    /** READ — GET /api/sales — all orders */
    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    /** READ — GET /api/sales/customer/{username} — orders for one customer */
    @GetMapping("/customer/{username}")
    public List<Order> getOrdersByCustomer(@PathVariable String username) {
        return orderRepo.findByCustomer(username);
    }

    /** READ (single) — GET /api/sales/{id} */
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return service.getOrderById(id);
    }

    /** UPDATE — POST /api/sales/update */
    @PostMapping("/update")
    public void updateOrder(@RequestBody Map<String, Object> payload) {
        String orderId    = (String) payload.get("orderId");
        double baseAmount = Double.parseDouble(payload.get("baseAmount").toString());
        String orderType  = payload.getOrDefault("orderType", "Retail").toString();
        String customer   = payload.getOrDefault("customerUsername", "").toString();

        Order order = "Wholesale".equals(orderType)
                ? new WholesaleOrder(orderId, baseAmount)
                : new RetailOrder(orderId, baseAmount);
        order.setCustomerUsername(customer);
        service.updateOrder(order);
    }

    /** DELETE — DELETE /api/sales/{id} */
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        service.deleteOrder(id);
    }
}
