package com.supermarket.inventory.entity;

public class Staff extends User {
    public Staff() {}
    public Staff(String username, String password) {
        super(username, password, "STAFF");
    }
    @Override
    public String getDashboardRoute() { return "/staff-dashboard.html"; }
}
