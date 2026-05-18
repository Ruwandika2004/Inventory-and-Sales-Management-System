package com.supermarket.inventory.entity;

public class PerishableProduct extends Product {
    private String expiryDate;

    public PerishableProduct() {}

    public PerishableProduct(String id, String name, double price, String expiryDate) {
        super(id, name, price, "Perishable");
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate()              { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    @Override
    public String getHandlingInstructions() {
        return "Keep refrigerated. Expiry date: " + expiryDate;
    }
}
