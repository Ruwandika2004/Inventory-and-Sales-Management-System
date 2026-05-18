package com.supermarket.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SupermarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermarketApplication.class, args);

        System.out.println("\n=======================================================");
        System.out.println("  🚀 SUPERMARKET SYSTEM IS RUNNING SUCCESSFULLY! 🚀");
        System.out.println("  👉 Access Sales List: http://localhost:8080/sales-list.html");
        System.out.println("=======================================================\n");
    }

    @GetMapping("/")
    public String index() {
        return "forward:/sales-list.html";
    }
}