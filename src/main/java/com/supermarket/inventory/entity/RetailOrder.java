package com.supermarket.inventory.entity;

public class RetailOrder extends Order {
    private double taxRate;

    public RetailOrder() { this.taxRate = 0.05; }

    public RetailOrder(String orderId, double baseAmount) {
        super(orderId, baseAmount);
        this.taxRate = 0.05; // 5% retail tax
    }

    public double getTaxRate()               { return taxRate; }
    public void setTaxRate(double taxRate)   { this.taxRate = taxRate; }

    @Override
    public double calculateTotal() {
        return getBaseAmount() + (getBaseAmount() * taxRate);
    }
}
