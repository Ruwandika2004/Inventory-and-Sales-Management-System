package com.supermarket.inventory.entity;

import java.time.LocalDateTime;

/**
 * Abstract Order class — demonstrates Abstraction and Polymorphism (OOP).
 * Subclasses: RetailOrder (5% tax), WholesaleOrder (10% discount)
 */
public abstract class Order {
    private String orderId;
    private double baseAmount;
    private LocalDateTime orderDate;
    private String customerUsername; // links sale to a customer

    public Order() {}

    public Order(String orderId, double baseAmount) {
        this.orderId    = orderId;
        this.baseAmount = baseAmount;
        this.orderDate  = LocalDateTime.now();
    }

    public String getOrderId()                   { return orderId; }
    public void setOrderId(String orderId)        { this.orderId = orderId; }

    public double getBaseAmount()                { return baseAmount; }
    public void setBaseAmount(double baseAmount) { this.baseAmount = baseAmount; }

    public LocalDateTime getOrderDate()          { return orderDate; }
    public void setOrderDate(LocalDateTime d)    { this.orderDate = d; }

    public String getCustomerUsername()                        { return customerUsername; }
    public void   setCustomerUsername(String customerUsername) { this.customerUsername = customerUsername; }

    // Polymorphism — each subclass calculates its own total
    public abstract double calculateTotal();
}

