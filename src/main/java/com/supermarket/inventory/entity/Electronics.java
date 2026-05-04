package com.supermarket.inventory.entity;

public class Electronics extends Product {
    private int warrantyMonths;

    public Electronics() {}

    public Electronics(String id, String name, double price, int warrantyMonths) {
        super(id, name, price, "Electronics");
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths()               { return warrantyMonths; }
    public void setWarrantyMonths(int w)         { this.warrantyMonths = w; }

    @Override
    public String getHandlingInstructions() {
        return "Handle with care. Warranty: " + warrantyMonths + " months.";
    }
}
