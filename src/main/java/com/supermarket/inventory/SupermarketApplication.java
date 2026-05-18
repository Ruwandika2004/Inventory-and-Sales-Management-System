package com.supermarket.inventory;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
=======
import com.supermarket.inventory.entity.Admin;
import com.supermarket.inventory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
>>>>>>> main
public class SupermarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermarketApplication.class, args);
<<<<<<< HEAD

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
=======
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
>>>>>>> main
