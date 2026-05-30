package com.rollingstars.model;

import java.sql.Timestamp;

public class BarTab {
    private int id;
    private String guestName;
    private int totalBill;
    private Timestamp createdAt; // 4th argument your Dashboard expects
    private String status;       // 5th argument your Dashboard expects

    // 1. The Empty Constructor (Required by ViewTabServlet)
    public BarTab() {
    }

    // 2. The 3-Argument Constructor
    public BarTab(int id, String guestName, int totalBill) {
        this.id = id;
        this.guestName = guestName;
        this.totalBill = totalBill;
    }

    // 3. The 5-Argument Constructor (Required by DashboardServlet to fix the crash!)
    public BarTab(int id, String guestName, int totalBill, Timestamp createdAt, String status) {
        this.id = id;
        this.guestName = guestName;
        this.totalBill = totalBill;
        this.createdAt = createdAt;
        this.status = status;
    }

    // --- Getters & Setters ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}