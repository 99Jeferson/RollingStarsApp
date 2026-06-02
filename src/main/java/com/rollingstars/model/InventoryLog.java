package com.rollingstars.model;

import java.sql.Timestamp;

public class InventoryLog {
    private int id;
    private String itemName;       // We will join tables to get the actual item name
    private int quantity;
    private String transactionType; // 'STOCK_IN' or 'SALE_DEDUCTION'
    private String performedBy;
    private Timestamp loggedAt;

    public InventoryLog() {}

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }

    public Timestamp getLoggedAt() { return loggedAt; }
    public void setLoggedAt(Timestamp loggedAt) { this.loggedAt = loggedAt; }
}