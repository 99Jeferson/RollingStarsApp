package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rollingstars.model.BarTab;
import com.rollingstars.model.InventoryItem;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view-tab")
public class ViewTabServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tabIdStr = request.getParameter("id");
        if (tabIdStr == null || tabIdStr.trim().isEmpty()) {
            response.sendRedirect("dashboard?error=Missing Tab Identification ID Parameter.");
            return;
        }

        int tabId = Integer.parseInt(tabIdStr);
        BarTab activeTab = null;
        List<InventoryItem> availableInventory = new ArrayList<>();

        String tabSql = "SELECT id, guest_name, total_bill, created_at, status FROM bar_tabs WHERE id = ?";
        String invSql = "SELECT id, item_name, price, stock_count FROM inventory WHERE stock_count > 0 ORDER BY item_name ASC";

        try (Connection conn = DBConnection.getConnection()) {
            
            // 1. Fetch current customer session billing metrics
            try (PreparedStatement tabStmt = conn.prepareStatement(tabSql)) {
                tabStmt.setInt(1, tabId);
                try (ResultSet rs = tabStmt.executeQuery()) {
                    if (rs.next()) {
                        activeTab = new BarTab(
                            rs.getInt("id"),
                            rs.getString("guest_name"),
                            rs.getInt("total_bill"),
                            rs.getTimestamp("created_at"),
                            rs.getString("status")
                        );
                    }
                }
            }

            // 2. Fetch inventory records to build the dynamic select dropdown panel
            try (PreparedStatement invStmt = conn.prepareStatement(invSql);
                 ResultSet invRs = invStmt.executeQuery()) {

                while (invRs.next()) {
                    InventoryItem item = new InventoryItem();
                    item.setId(invRs.getInt("id"));
                    item.setItemName(invRs.getString("item_name"));
                    item.setUnitPrice(invRs.getInt("price"));         // Mapped to updated 'price' column
                    item.setStockQty(invRs.getInt("stock_count"));     // Mapped to updated 'stock_count' column
                    availableInventory.add(item);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database Error pulling live inventory list!");
            e.printStackTrace();
        }

        if (activeTab == null) {
            response.sendRedirect("dashboard?error=Requested bar session tab data was not found.");
            return;
        }

        request.setAttribute("tab", activeTab);
        request.setAttribute("inventoryList", availableInventory);
        request.getRequestDispatcher("view-tab.jsp").forward(request, response);
    }
}