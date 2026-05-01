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


    public String login(String username, String password) {
        Optional<User> user = repository.findByUsernameAndPassword(username, password);
        return user.map(User::getDashboardRoute).orElse("ERROR");
    }


    public List<User> getAllUsers() {
        return repository.findAll();
    }


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
