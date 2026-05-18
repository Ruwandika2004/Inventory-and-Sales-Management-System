package com.supermarket.inventory.entity;

public class StockReport extends Report {
    private int totalItemsInStock;

    public StockReport() {}

    public StockReport(String reportId, int totalItemsInStock) {
        super(reportId);
        this.totalItemsInStock = totalItemsInStock;
    }

    public int getTotalItemsInStock() { return totalItemsInStock; }
    public void setTotalItemsInStock(int totalItemsInStock) { this.totalItemsInStock = totalItemsInStock; }

    @Override
    public String generateReportContent() {
        return "Stock Report [ID: " + getReportId() + "] - Total Items in Stock: " + totalItemsInStock;
    }
}

