package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;


@Repository
public class TxtUserRepositoryImpl implements UserRepository {

    private static final String FILE_PATH = "src/main/resources/data/users.txt";

    @Override
    public void save(User user) {
        List<User> users = findAll();
        users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        users.add(user);
        writeAll(users);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 3) continue;

                String role     = parts[0];
                String username = parts[1];
                String password = parts[2];

                if ("ADMIN".equals(role)) {
                    list.add(new Admin(username, password));
                } else if ("CUSTOMER".equals(role)) {
                    String email          = parts.length > 3 ? parts[3] : "";
                    String phone          = parts.length > 4 ? parts[4] : "";
                    String membershipType = parts.length > 5 ? parts[5] : "REGULAR";
                    list.add(new Customer(username, password, email, phone, membershipType));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> findAllCustomers() {
        List<User> all = findAll();
        all.removeIf(u -> !"CUSTOMER".equals(u.getRole()));
        return all;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public void deleteById(String username) {
        List<User> users = findAll();
        users.removeIf(u -> u.getUsername().equals(username));
        writeAll(users);
    }

    private void writeAll(List<User> users) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (User u : users) {
                if (u instanceof Customer c) {
                    // CUSTOMER|username|password|email|phone|membershipType
                    writer.write("CUSTOMER|" + c.getUsername() + "|" + c.getPassword()
                            + "|" + nvl(c.getEmail())
                            + "|" + nvl(c.getPhone())
                            + "|" + nvl(c.getMembershipType()));
                } else {

                    writer.write(u.getRole() + "|" + u.getUsername() + "|" + u.getPassword() + "|||");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nvl(String s) { return s == null ? "" : s; }
}
