package com.supermarket.inventory.entity;

public class SalesReport extends Report {
    private double totalRevenue;

    public SalesReport() {}

    public SalesReport(String reportId, double totalRevenue) {
        super(reportId);
        this.totalRevenue = totalRevenue;
    }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    @Override
    public String generateReportContent() {
        return "Sales Report [ID: " + getReportId() + "] - Total Revenue: LKR " + totalRevenue;
    }
}
