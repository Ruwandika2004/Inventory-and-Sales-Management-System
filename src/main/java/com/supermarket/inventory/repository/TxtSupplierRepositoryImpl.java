package com.supermarket.inventory.repository;

import com.supermarket.inventory.entity.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

/**
 * File I/O implementation of SupplierRepository.
 * Stores data in: src/main/resources/data/suppliers.txt
 *
 * File format (pipe-delimited):
 *   Local|id|name|contactInfo|region
 *   International|id|name|contactInfo|country
 */
@Repository
public class TxtSupplierRepositoryImpl implements SupplierRepository {

    private static final String FILE_PATH = "src/main/resources/data/suppliers.txt";

    @Override
    public void save(Supplier supplier) {
        List<Supplier> suppliers = findAll();
        suppliers.removeIf(s -> s.getId().equals(supplier.getId()));
        suppliers.add(supplier);
        writeAll(suppliers);
    }

    @Override
    public List<Supplier> findAll() {
        List<Supplier> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                String type    = parts[0];
                String id      = parts[1];
                String name    = parts[2];
                String contact = parts[3];

                if ("Local".equals(type)) {
                    String region = parts.length > 4 ? parts[4] : "";
                    list.add(new LocalSupplier(id, name, contact, region));
                } else if ("International".equals(type)) {
                    String country = parts.length > 4 ? parts[4] : "";
                    list.add(new InternationalSupplier(id, name, contact, country));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Supplier> findById(String id) {
        return findAll().stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(String id) {
        List<Supplier> suppliers = findAll();
        suppliers.removeIf(s -> s.getId().equals(id));
        writeAll(suppliers);
    }

    private void writeAll(List<Supplier> suppliers) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Supplier s : suppliers) {
                String line;
                if (s instanceof LocalSupplier ls) {
                    line = "Local|" + s.getId() + "|" + s.getName() + "|" + s.getContactInfo() + "|" + ls.getRegion();
                } else if (s instanceof InternationalSupplier is) {
                    line = "International|" + s.getId() + "|" + s.getName() + "|" + s.getContactInfo() + "|" + is.getCountry();
                } else {
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
