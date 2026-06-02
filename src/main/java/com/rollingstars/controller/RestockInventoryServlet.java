package com.rollingstars.controller;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rollingstars.model.InventoryItem;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ "/inventory-control", "/RestockInventoryServlet" })
public class RestockInventoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 1. GET ROUTE: Fetches the entire warehouse listing to render on the Manager's control board
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<InventoryItem> fullInventory = new ArrayList<>();
        String sql = "SELECT * FROM inventory ORDER BY item_name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InventoryItem item = new InventoryItem();
                item.setId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setUnitPrice(rs.getInt("unit_price"));
                item.setStockQty(rs.getInt("stock_qty"));
                fullInventory.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Database error pulling full management inventories!");
            e.printStackTrace();
        }

        request.setAttribute("fullInventory", fullInventory);
        request.getRequestDispatcher("inventory-management.jsp").forward(request, response);
    }

    // 2. POST ROUTE: Captures incoming manager restocks and calls the secure stored procedure
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemIdStr = request.getParameter("itemId");
        String restockQtyStr = request.getParameter("restockQty");

        if (itemIdStr != null && restockQtyStr != null) {
            int itemId = Integer.parseInt(itemIdStr);
            int restockQty = Integer.parseInt(restockQtyStr);
            
            // In a real application, you would pull the logged-in manager's username from the Session
            String managerUser = "Manager_Jane"; 

            // CALL OUT THE SECURE MYSQL STORED PROCEDURE Blueprint
            String procedureCall = "{CALL sp_RestockItem(?, ?, ?)}";

            try (Connection conn = DBConnection.getConnection();
                 CallableStatement stmt = conn.prepareCall(procedureCall)) {
                
                stmt.setInt(1, itemId);
                stmt.setInt(2, restockQty);
                stmt.setString(3, managerUser);
                
                // Execute the transaction cleanly inside MySQL
                stmt.execute();
                System.out.println("Stored Procedure Executed Successfully! Stock incremented and audited.");

            } catch (SQLException e) {
                System.out.println("Critical Error calling stored procedure transaction!");
                e.printStackTrace();
            }
        }

        // Send the manager back to the refreshed control board inventory screen
        response.sendRedirect("inventory-control");
    }
}