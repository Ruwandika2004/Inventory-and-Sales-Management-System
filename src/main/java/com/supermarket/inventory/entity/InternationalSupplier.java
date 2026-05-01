package com.supermarket.inventory.entity;

public class InternationalSupplier extends Supplier {
    private String country;

    public InternationalSupplier() {}

    public InternationalSupplier(String id, String name, String contactInfo, String country) {
        super(id, name, contactInfo);
        this.country = country;
    }

    public String getCountry()             { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String getShippingDetails() {
        return "International shipping via air freight from: " + country;
    }
}
