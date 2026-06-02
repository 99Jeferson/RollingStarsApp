package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rollingstars.model.BarTab;
import com.rollingstars.model.InventoryItem; // Added to handle stock items
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BarTab> tabList = new ArrayList<>();
        List<InventoryItem> availableInventory = new ArrayList<>(); // Added to store menu stock
        
        // SQL Queries
        String tabSql = "SELECT id, guest_name, total_bill, created_at, status FROM bar_tabs WHERE status = 'ACTIVE' ORDER BY created_at DESC";
        String invSql = "SELECT id, item_name, unit_price, stock_qty FROM inventory WHERE stock_qty > 0 ORDER BY item_name ASC";

        // Open a single connection block to process both statements cleanly
        try (Connection conn = DBConnection.getConnection()) {
            
            // TASK 1: Fetch active tracking tables for the live floor view
            try (PreparedStatement stmt = conn.prepareStatement(tabSql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    BarTab tab = new BarTab(
                        rs.getInt("id"),
                        rs.getString("guest_name"),
                        rs.getInt("total_bill"),
                        rs.getTimestamp("created_at"),
                        rs.getString("status") 
                    );
                    tabList.add(tab);
                }
            }

            // TASK 2: Fetch any active stock rooms entries with quantities > 0 for the dropdown menu
            try (PreparedStatement invStmt = conn.prepareStatement(invSql);
                 ResultSet invRs = invStmt.executeQuery()) {

                while (invRs.next()) {
                    InventoryItem item = new InventoryItem();
                    item.setId(invRs.getInt("id"));
                    item.setItemName(invRs.getString("item_name"));
                    item.setUnitPrice(invRs.getInt("unit_price"));
                    item.setStockQty(invRs.getInt("stock_qty"));
                    availableInventory.add(item);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error compiling live tracking dashboard data sets!");
            e.printStackTrace();
        }
        
        // Attach both arrays to the request pipeline parameters
        request.setAttribute("activeTabs", tabList);
        request.setAttribute("inventoryList", availableInventory); // Sent directly to the front-end dropdown form

        // Forward the control directly over to your front-end view
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}