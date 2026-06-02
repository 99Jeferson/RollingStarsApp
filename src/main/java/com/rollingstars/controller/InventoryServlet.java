package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.rollingstars.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inventory-control")
public class InventoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        ArrayList<Map<String, Object>> inventoryList = new ArrayList<>();
        String sql = "SELECT id, item_name, stock_count, price, category FROM inventory ORDER BY stock_count ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", rs.getInt("id"));
                item.put("itemName", rs.getString("item_name"));
                item.put("stockCount", rs.getInt("stock_count"));
                item.put("price", rs.getDouble("price"));
                item.put("category", rs.getString("category"));
                inventoryList.add(item);
            }
            
            request.setAttribute("inventoryData", inventoryList);
            request.getRequestDispatcher("inventory.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("dashboard?error=Database Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action"); 
        String itemIdStr = request.getParameter("itemId");

        if (itemIdStr == null || action == null) {
            response.sendRedirect("inventory-control?error=Missing required inputs.");
            return;
        }

        int itemId = Integer.parseInt(itemIdStr);

        try (Connection conn = DBConnection.getConnection()) {
            
            if ("restock".equals(action)) {
                int quantityToAdd = Integer.parseInt(request.getParameter("quantity"));
                String sql = "UPDATE inventory SET stock_count = stock_count + ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, quantityToAdd);
                    stmt.setInt(2, itemId);
                    stmt.executeUpdate();
                    response.sendRedirect("inventory-control?success=Stock restocked successfully!");
                }
                
            } else if ("updatePrice".equals(action)) {
                int newPrice = Integer.parseInt(request.getParameter("newPrice"));
                String sql = "UPDATE inventory SET price = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, newPrice);
                    stmt.setInt(2, itemId);
                    stmt.executeUpdate();
                    response.sendRedirect("inventory-control?success=Item price updated successfully!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("inventory-control?error=Operation failed: " + e.getMessage());
        }
    }
}