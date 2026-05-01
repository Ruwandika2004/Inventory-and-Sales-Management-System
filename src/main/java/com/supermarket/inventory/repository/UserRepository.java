package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    List<User> findAll();
    Optional<User> findByUsernameAndPassword(String username, String password);
    void deleteById(String username);
}
