package com.rollingstars.model;

import java.sql.Timestamp;

public class BarTab {
    private int id;
    private String guestName;
    private int totalBill;
    private Timestamp createdAt;
    private String status; // New Field

    // Updated Constructor
    public BarTab(int id, String guestName, int totalBill, Timestamp createdAt, String status) {
        this.id = id;
        this.guestName = guestName;
        this.totalBill = totalBill;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getGuestName() { return guestName; }
    public int getTotalBill() { return totalBill; }
    public Timestamp getCreatedAt() { return createdAt; }
    public String getStatus() { return status; } // New Getter
}