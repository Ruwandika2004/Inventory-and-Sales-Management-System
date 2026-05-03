package com.supermarket.inventory.entity;

/**
 * Abstract User class — demonstrates Abstraction and Inheritance (OOP).
 * Subclasses: Admin, Staff
 */
public abstract class User {
    private String username;
    private String password;
    private String role; // "ADMIN" or "STAFF"

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername()              { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword()              { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole()                  { return role; }
    public void setRole(String role)         { this.role = role; }

    /**
     * Validates the user credentials.
     * @return true if valid, false otherwise.
     */
    public boolean validate() {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        if (role == null || role.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    // Polymorphism — each subclass returns its own dashboard route
    public abstract String getDashboardRoute();
}
