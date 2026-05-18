package com.supermarket.inventory.entity;

/**
 * Abstract Supplier class — demonstrates Abstraction and Inheritance (OOP).
 * Subclasses: LocalSupplier, InternationalSupplier
 */
public abstract class Supplier {
    private String id;
    private String name;
    private String contactInfo;

    public Supplier() {}

    public Supplier(String id, String name, String contactInfo) {
        this.id          = id;
        this.name        = name;
        this.contactInfo = contactInfo;
    }

    public String getId()                        { return id; }
    public void setId(String id)                 { this.id = id; }

    // Legacy accessor kept for compatibility
    public String getSupplierId()                { return id; }
    public void setSupplierId(String id)         { this.id = id; }

    public String getName()                      { return name; }
    public void setName(String name)             { this.name = name; }

    public String getContactInfo()               { return contactInfo; }
    public void setContactInfo(String c)         { this.contactInfo = c; }

    public abstract String getShippingDetails();
}
