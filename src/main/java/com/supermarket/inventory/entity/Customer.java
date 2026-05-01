package com.supermarket.inventory.entity;

/**
 * Customer — a registered buyer in the supermarket system.
 *
 * OOP Concepts:
 *  - Inheritance   : extends abstract User class
 *  - Encapsulation : private fields with getters/setters
 *  - Polymorphism  : overrides getDashboardRoute() to return customer portal
 */
public class Customer extends User {

    private String email;
    private String phone;
    private String membershipType; // "REGULAR" or "PREMIUM"

    public Customer() {}

    public Customer(String username, String password,
                    String email, String phone, String membershipType) {
        super(username, password, "CUSTOMER");
        this.email          = email;
        this.phone          = phone;
        this.membershipType = membershipType;
    }

    /** Polymorphism — Customer is routed to their own shopping portal */
    @Override
    public String getDashboardRoute() {
        return "/customer-profile.html";
    }

    // ── Getters & Setters (Encapsulation) ───────────────────────────────────

    public String getEmail()                         { return email; }
    public void   setEmail(String email)             { this.email = email; }

    public String getPhone()                         { return phone; }
    public void   setPhone(String phone)             { this.phone = phone; }

    public String getMembershipType()                { return membershipType; }
    public void   setMembershipType(String type)     { this.membershipType = type; }
}
