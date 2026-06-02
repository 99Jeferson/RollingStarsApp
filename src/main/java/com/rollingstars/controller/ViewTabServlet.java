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
        BarTab tab = null;
        List<InventoryItem> availableInventory = new ArrayList<>();

        // 1. Fetch the active customer tab context
        if (tabIdStr != null && !tabIdStr.trim().isEmpty()) {
            int tabId = Integer.parseInt(tabIdStr);
            String tabSql = "SELECT * FROM bar_tabs WHERE id = ?"; 

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(tabSql)) {
                stmt.setInt(1, tabId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        tab = new BarTab();
                        tab.setId(rs.getInt("id"));
                        tab.setGuestName(rs.getString("guest_name"));
                        tab.setTotalBill(rs.getInt("total_bill"));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database Error fetching active tab!");
                e.printStackTrace();
            }
        }

        // 2. Fetch all items in stock for the bartender dropdown selector
        String invSql = "SELECT * FROM inventory WHERE stock_qty > 0 ORDER BY item_name ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(invSql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                InventoryItem item = new InventoryItem();
                item.setId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setUnitPrice(rs.getInt("unit_price"));
                item.setStockQty(rs.getInt("stock_qty"));
                availableInventory.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Database Error pulling live inventory list!");
            e.printStackTrace();
        }

        // 3. Route or fallback based on search context safety
        if (tab != null) {
            request.setAttribute("tab", tab);
            request.setAttribute("inventoryList", availableInventory); // Sent directly to UI drop-down
            request.getRequestDispatcher("view-tab.jsp").forward(request, response);
        } else {
            response.sendRedirect("dashboard");
        }
    }
}