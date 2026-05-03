package com.supermarket.inventory.controller;

import com.supermarket.inventory.entity.Report;
import com.supermarket.inventory.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ReportController — full CRUD for saved reports.
 *
 * CREATE  POST   /api/reports          → generate & save a new report snapshot
 * READ    GET    /api/reports          → list all saved reports
 * READ    GET    /api/reports/{id}     → get one saved report by ID
 * UPDATE  POST   /api/reports/{id}/regenerate → refresh report data
 * DELETE  DELETE /api/reports/{id}    → remove a saved report
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** CREATE — generate and persist a new report snapshot */
    @PostMapping
    public List<Report> createReport() {
        return service.generateAndSaveReports();
    }

    /** CREATE — manually create a report with specific data */
    @PostMapping("/manual")
    public void createManualReport(@RequestBody java.util.Map<String, Object> payload) {
        String id = (String) payload.get("id");
        String type = (String) payload.get("type");
        String date = (String) payload.get("date");
        
        if (id == null || id.trim().isEmpty() || type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("ID and Type are required for manual reports.");
        }
        
        double value = Double.parseDouble(payload.get("value").toString());
        if (value < 0) {
            throw new IllegalArgumentException("Report value cannot be negative.");
        }
        
        service.createManualReport(type, id, value, date);
    }

    /** READ — list all saved reports */
    @GetMapping
    public List<Report> getAllReports() {
        return service.getSavedReports();
    }

    /** READ — single report by ID */
    @GetMapping("/{id}")
    public Report getReportById(@PathVariable String id) {
        return service.getReportById(id);
    }

    /** UPDATE — manually edit report value */
    @PostMapping("/update")
    public void updateReport(@RequestBody java.util.Map<String, Object> payload) {
        String oldId = (String) payload.get("oldId");
        String newId = (String) payload.get("newId");
        String type = (String) payload.get("type");
        String date = (String) payload.get("date");
        
        if (oldId == null || newId == null || type == null) {
            throw new IllegalArgumentException("Old ID, New ID, and Type are required for updates.");
        }

        double value = Double.parseDouble(payload.get("value").toString());
        if (value < 0) {
            throw new IllegalArgumentException("Updated value cannot be negative.");
        }
        
        if ("sales".equals(type)) {
            service.updateReport(oldId, newId, value, 0, date);
        } else {
            service.updateReport(oldId, newId, 0, (int) value, date);
        }
    }

    /** UPDATE — regenerate/refresh an existing report */
    @PostMapping("/{id}/regenerate")
    public List<Report> regenerateReport(@PathVariable String id) {
        service.deleteReport(id);
        return service.generateAndSaveReports();
    }

    /** DELETE — remove a saved report */
    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable String id) {
        service.deleteReport(id);
    }
}
