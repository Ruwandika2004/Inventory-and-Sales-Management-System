package com.supermarket.inventory.controller;

import com.supermarket.inventory.entity.*;
import com.supermarket.inventory.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ── Auth ────────────────────────────────────────────────────────────────

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> creds) {
        return service.login(creds.get("username"), creds.get("password"));
    }

    // ── Admin CRUD ───────────────────────────────────────────────────────────

    /** Create an Admin account (used by admin panel) */
    @PostMapping("/register")
    public void registerAdmin(@RequestBody Map<String, String> payload) {
        service.registerUser(new Admin(payload.get("username"), payload.get("password")));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/update")
    public void updateAdmin(@RequestBody Map<String, String> payload) {
        service.updateUser(new Admin(payload.get("username"), payload.get("password")));
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestBody Map<String, String> payload) {
        service.deleteUser(payload.get("username"));
    }

    // ── Customer CRUD ────────────────────────────────────────────────────────

    /** Self-registration by a new customer */
    @PostMapping("/customers/register")
    public void registerCustomer(@RequestBody Map<String, String> p) {
        service.registerUser(new Customer(
                p.get("username"), p.get("password"),
                p.get("email"),    p.get("phone"),
                p.getOrDefault("membershipType", "REGULAR")
        ));
    }

    /** Admin view — all customers */
    @GetMapping("/customers")
    public List<User> getAllCustomers() {
        return service.getAllCustomers();
    }

    /** Customer updates their own profile */
    @PostMapping("/customers/update")
    public void updateCustomer(@RequestBody Map<String, String> p) {
        service.updateUser(new Customer(
                p.get("username"), p.get("password"),
                p.get("email"),    p.get("phone"),
                p.getOrDefault("membershipType", "REGULAR")
        ));
    }
}
