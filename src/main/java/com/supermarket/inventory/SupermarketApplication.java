package com.supermarket.inventory;

import com.supermarket.inventory.entity.Admin;
import com.supermarket.inventory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SupermarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermarketApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedAdminUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.findAll().isEmpty()) {
                userRepository.save(new Admin("admin", "admin123"));
                System.out.println("✅ Default admin created — username: admin | password: admin123");
            }
        };
    }
}
