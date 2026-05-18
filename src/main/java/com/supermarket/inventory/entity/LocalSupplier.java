package com.supermarket.inventory.entity;

public class LocalSupplier extends Supplier {
    private String region;

    public LocalSupplier() {}

    public LocalSupplier(String id, String name, String contactInfo, String region) {
        super(id, name, contactInfo);
        this.region = region;
    }

    public String getRegion()            { return region; }
    public void setRegion(String region) { this.region = region; }

    @Override
    public String getShippingDetails() {
        return "Local delivery via truck in region: " + region;
    }
}
