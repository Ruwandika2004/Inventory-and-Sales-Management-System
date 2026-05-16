package com.supermarket.inventory.service;

import com.supermarket.inventory.entity.Report;
import com.supermarket.inventory.entity.SalesReport;
import com.supermarket.inventory.entity.StockReport;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportService {

    private static final String FILE_PATH = "src/main/resources/data/reports.txt";

    private final SalesService salesService;
    private final StockService stockService;

    public ReportService(SalesService salesService, StockService stockService) {
        this.salesService = salesService;
        this.stockService = stockService;
    }

    // ── CREATE ───────────────────────────────────────────────────────────────

    /** Generate a fresh snapshot from live data and save it to reports.txt */
    public List<Report> generateAndSaveReports() {
        double totalRevenue = salesService.getAllOrders().stream()
                .mapToDouble(o -> o.calculateTotal()).sum();
        int totalStock = stockService.getAllTransactions().stream()
                .mapToInt(t -> "IN".equals(t.getType()) ? t.getQuantity() : -t.getQuantity()).sum();

        SalesReport sr = new SalesReport(UUID.randomUUID().toString(), totalRevenue);
        StockReport st = new StockReport(UUID.randomUUID().toString(), totalStock);

        List<Report> current = getSavedReports();
        current.add(sr);
        current.add(st);
        writeAll(current);

        return List.of(sr, st);
    }

    /** Create a new report manually with custom user data */
    public void createManualReport(String type, String id, double value, String dateStr) {
        List<Report> reports = getSavedReports();
        LocalDateTime dt = (dateStr != null && !dateStr.trim().isEmpty())
                ? LocalDateTime.parse(dateStr.trim())
                : LocalDateTime.now();

        if ("sales".equalsIgnoreCase(type)) {
            SalesReport sr = new SalesReport(id, value);
            sr.setGeneratedAt(dt);
            reports.add(sr);
        } else {
            StockReport st = new StockReport(id, (int)value);
            st.setGeneratedAt(dt);
            reports.add(st);
        }
        writeAll(reports);
    }

    // ── READ ─────────────────────────────────────────────────────────────────

    /** Return all saved reports from reports.txt */
    public List<Report> getSavedReports() {
        List<Report> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 4) continue;

                String type      = p[0];
                String reportId  = p[1];
                LocalDateTime dt = LocalDateTime.parse(p[3]);

                if ("SALES".equals(type)) {
                    SalesReport sr = new SalesReport(reportId, Double.parseDouble(p[2]));
                    sr.setGeneratedAt(dt);
                    list.add(sr);
                } else if ("STOCK".equals(type)) {
                    StockReport st = new StockReport(reportId, Integer.parseInt(p[2]));
                    st.setGeneratedAt(dt);
                    list.add(st);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Return one saved report by ID */
    public Report getReportById(String id) {
        return getSavedReports().stream()
                .filter(r -> r.getReportId().equals(id))
                .findFirst().orElse(null);
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────

    /** Update a saved report manually (for CRUD demonstration) */
    public void updateReport(String oldId, String newId, double newRevenue, int newStock, String newDate) {
        List<Report> reports = getSavedReports();
        for (Report r : reports) {
            if (r.getReportId().equals(oldId)) {
                if (newId != null && !newId.trim().isEmpty()) {
                    r.setReportId(newId.trim());
                }
                if (newDate != null && !newDate.trim().isEmpty()) {
                    try { r.setGeneratedAt(LocalDateTime.parse(newDate.trim())); } catch (Exception e) {}
                }
                if (r instanceof SalesReport sr) {
                    sr.setTotalRevenue(newRevenue);
                } else if (r instanceof StockReport st) {
                    st.setTotalItemsInStock(newStock);
                }
                break;
            }
        }
        writeAll(reports);
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    /** Remove a saved report by ID */
    public void deleteReport(String id) {
        List<Report> reports = getSavedReports();
        reports.removeIf(r -> r.getReportId().equals(id));
        writeAll(reports);
    }

    // ── LEGACY (keeps old GET /api/reports working if anything uses it) ──────
    public List<Report> generateAllReports() {
        return generateAndSaveReports();
    }

    // ── File I/O ─────────────────────────────────────────────────────────────
    private void writeAll(List<Report> reports) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Report r : reports) {
                String dt   = r.getGeneratedAt() != null ? r.getGeneratedAt().toString() : LocalDateTime.now().toString();
                String line;
                if (r instanceof SalesReport sr) {
                    line = "SALES|" + r.getReportId() + "|" + sr.getTotalRevenue() + "|" + dt;
                } else if (r instanceof StockReport st) {
                    line = "STOCK|" + r.getReportId() + "|" + st.getTotalItemsInStock() + "|" + dt;
                } else { continue; }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
