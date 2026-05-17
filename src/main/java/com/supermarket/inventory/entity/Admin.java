package com.supermarket.inventory.entity;


public class Admin extends User {
    public Admin() {}
    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }
    @Override
    public String getDashboardRoute() { return "/admin-dashboard.html"; }
}

