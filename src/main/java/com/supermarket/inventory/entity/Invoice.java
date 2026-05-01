package com.supermarket.inventory.entity;

import java.time.LocalDateTime;

public class Invoice {
    private String invoiceId;
    private Order order;
    private LocalDateTime issueDate;

    public Invoice() {}

    public Invoice(String invoiceId, Order order) {
        this.invoiceId = invoiceId;
        this.order = order;
        this.issueDate = LocalDateTime.now();
    }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }
}
