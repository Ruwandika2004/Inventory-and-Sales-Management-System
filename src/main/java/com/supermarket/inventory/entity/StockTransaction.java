package com.supermarket.inventory.entity;

import java.time.LocalDateTime;

public class StockTransaction {
    private String transactionId;
    private String productId;
    private int quantity;
    private String type; // "IN" or "OUT"
    private LocalDateTime timestamp;

    public StockTransaction() {}

    public StockTransaction(String transactionId, String productId, int quantity, String type) {
        this.transactionId = transactionId;
        this.productId     = productId;
        this.quantity      = quantity;
        this.type          = type;
        this.timestamp     = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getType()  {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LocalDateTime getTimestamp()   {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
