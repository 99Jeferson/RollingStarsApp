package com.rollingstars.model;

import java.getTimestamp; // standard timestamp handles date/time
import java.sql.Timestamp;

public class BarTab {
    private int id;
    private String guestName;
    private int totalBill;
    private Timestamp createdAt;

    // Constructor
    public BarTab(int id, String guestName, int totalBill, Timestamp createdAt) {
        this.id = id;
        this.guestName = guestName;
        this.totalBill = totalBill;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { 
    	return id; }
    public String getGuestName() { 
    	return guestName; }
    public int getTotalBill() { 
    	return totalBill; }
    public Timestamp getCreatedAt() { 
    	return createdAt; }
}