package com.supermarket.inventory.entity;

import java.time.LocalDateTime;

public abstract class Report {
    private String reportId;
    private LocalDateTime generatedAt;

    public Report() {}

    public Report(String reportId) {
        this.reportId    = reportId;
        this.generatedAt = LocalDateTime.now();
    }

    public String getReportId()                      { return reportId; }
    public void setReportId(String reportId)         { this.reportId = reportId; }

    public LocalDateTime getGeneratedAt()            { return generatedAt; }
    public void setGeneratedAt(LocalDateTime g)      { this.generatedAt = g; }

    public abstract String generateReportContent();
}

