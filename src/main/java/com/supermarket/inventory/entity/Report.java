package com.supermarket.inventory.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

/**
 * Abstract Report base class.
 * @JsonTypeInfo/@JsonSubTypes tell Jackson how to serialize
 * polymorphic Report objects into JSON correctly.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = SalesReport.class, name = "sales"),
    @JsonSubTypes.Type(value = StockReport.class, name = "stock")
})
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

    /**
     * Validates the report.
     * @return true if valid, false otherwise.
     */
    public boolean validate() {
        return reportId != null && !reportId.trim().isEmpty();
    }

    public abstract String generateReportContent();
}
