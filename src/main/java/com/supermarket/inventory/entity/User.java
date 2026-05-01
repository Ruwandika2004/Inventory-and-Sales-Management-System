package com.supermarket.inventory.entity;

<<<<<<< HEAD
public abstract class User {
    private String username;
    private String password;
    private String role;

    public User() {
    }
=======
/**
 * Abstract User class — demonstrates Abstraction and Inheritance (OOP).
 * Subclasses: Admin, Staff
 */
public abstract class User {
    private String username;
    private String password;
    private String role; // "ADMIN" or "STAFF"

    public User() {}
>>>>>>> 8cccf84 (Added report files)

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
<<<<<<< HEAD
        this.role = role;
    }
}
=======
        this.role     = role;
    }

    public String getUsername()              { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword()              { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole()                  { return role; }
    public void setRole(String role)         { this.role = role; }

    // Polymorphism — each subclass returns its own dashboard route
    public abstract String getDashboardRoute();
}
>>>>>>> 8cccf84 (Added report files)
