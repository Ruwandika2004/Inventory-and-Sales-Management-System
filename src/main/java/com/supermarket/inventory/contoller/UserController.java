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


    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> creds) {
        return service.login(creds.get("username"), creds.get("password"));
    }


}