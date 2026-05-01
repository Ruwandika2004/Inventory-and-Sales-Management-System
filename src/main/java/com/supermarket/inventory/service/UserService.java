package com.supermarket.inventory.service;

import com.supermarket.inventory.entity.*;
import com.supermarket.inventory.repository.TxtUserRepositoryImpl;
import com.supermarket.inventory.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final TxtUserRepositoryImpl txtRepo;

    public UserService(UserRepository repository, TxtUserRepositoryImpl txtRepo) {
        this.repository = repository;
        this.txtRepo    = txtRepo;
    }

    /** Register any User (Admin or Customer) */
    public void registerUser(User user) {
        repository.save(user);
    }

    /**
     * Login — returns the dashboard route for the matched user.
     * Polymorphism: getDashboardRoute() differs per subclass.
     */
    public String login(String username, String password) {
        Optional<User> user = repository.findByUsernameAndPassword(username, password);
        return user.map(User::getDashboardRoute).orElse("ERROR");
    }

    /** All users (Admin + Customer) */
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /** Customers only — used by admin customer list */
    public List<User> getAllCustomers() {
        return txtRepo.findAllCustomers();
    }

    public void updateUser(User user) {
        repository.save(user);
    }

    public void deleteUser(String username) {
        repository.deleteById(username);
    }
}
