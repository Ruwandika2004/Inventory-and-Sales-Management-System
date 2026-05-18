package com.supermarket.inventory.entity;

public class WholesaleOrder extends Order {
    private double discountRate;

    public WholesaleOrder() { this.discountRate = 0.10; }

    public WholesaleOrder(String orderId, double baseAmount) {
        super(orderId, baseAmount);
        this.discountRate = 0.10; // 10% wholesale discount
    }

    public double getDiscountRate()                  { return discountRate; }
    public void setDiscountRate(double discountRate) { this.discountRate = discountRate; }

    @Override
    public double calculateTotal() {
        return getBaseAmount() - (getBaseAmount() * discountRate);
    }
}
