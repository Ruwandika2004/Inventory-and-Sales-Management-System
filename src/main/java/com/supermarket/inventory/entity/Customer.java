package com.supermarket.inventory.entity;


public class Customer extends User {

    private String email;
    private String phone;
    private String membershipType;

    public Customer() {}

    public Customer(String username, String password,
                    String email, String phone, String membershipType) {
        super(username, password, "CUSTOMER");
        this.email          = email;
        this.phone          = phone;
        this.membershipType = membershipType;
    }


    @Override
    public String getDashboardRoute() {
        return "/customer-profile.html";
    }

    public String getEmail()                         { return email; }
    public void   setEmail(String email)             { this.email = email; }

    public String getPhone()                         { return phone; }
    public void   setPhone(String phone)             { this.phone = phone; }

    public String getMembershipType()                { return membershipType; }
    public void   setMembershipType(String type)     { this.membershipType = type; }
}
