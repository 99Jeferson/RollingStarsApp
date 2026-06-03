package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.rollingstars.model.User;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateBillServlet")
public class UpdateBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DYNAMIC RESOLUTION: Read the User object matching LoginServlet setup
        String activeUser = "System_Staff";
        HttpSession session = request.getSession(false);
        if (session != null) {
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser != null) {
                activeUser = currentUser.getUsername(); // Extracts name safely from your model
            }
        }

        String tabIdStr = request.getParameter("tabId");
        String itemIdStr = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");

        if (tabIdStr != null && itemIdStr != null && quantityStr != null) {
            int tabId = Integer.parseInt(tabIdStr);
            int itemId = Integer.parseInt(itemIdStr);
            int quantity = Integer.parseInt(quantityStr);

            Connection conn = null;

            try {
                conn = DBConnection.getConnection();
                conn.setAutoCommit(false);

                String itemSql = "SELECT price, stock_count FROM inventory WHERE id = ?";
                int itemPrice = 0;
                int currentStock = 0;

                try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                    itemStmt.setInt(1, itemId);
                    try (ResultSet rs = itemStmt.executeQuery()) {
                        if (rs.next()) {
                            itemPrice = rs.getInt("price");
                            currentStock = rs.getInt("stock_count");
                        } else {
                            throw new SQLException("Item not found in inventory registry.");
                        }
                    }
                }

                if (currentStock < quantity) {
                    throw new SQLException("Insufficient stock! Only " + currentStock + " units left.");
                }

                int addedCost = itemPrice * quantity;
                String updateTabSql = "UPDATE bar_tabs SET total_bill = total_bill + ? WHERE id = ? AND status = 'ACTIVE'";
                try (PreparedStatement tabStmt = conn.prepareStatement(updateTabSql)) {
                    tabStmt.setInt(1, addedCost);
                    tabStmt.setInt(2, tabId);
                    int rowsUpdated = tabStmt.executeUpdate();
                    if (rowsUpdated == 0) {
                        throw new SQLException("Failed to update bill. Tab may no longer be active.");
                    }
                }

                String deductSql = "UPDATE inventory SET stock_count = stock_count - ? WHERE id = ?";
                try (PreparedStatement deductStmt = conn.prepareStatement(deductSql)) {
                    deductStmt.setInt(1, quantity);
                    deductStmt.setInt(2, itemId);
                    deductStmt.executeUpdate();
                }

                // Logs action attributed directly to the logged-in staff member
                String logSql = "INSERT INTO inventory_logs (item_id, quantity, transaction_type, performed_by) VALUES (?, ?, 'SALE_DEDUCTION', ?)";
                try (PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                    logStmt.setInt(1, itemId);
                    logStmt.setInt(2, quantity);
                    logStmt.setString(3, activeUser); 
                    logStmt.executeUpdate();
                }

                conn.commit();
                response.sendRedirect("dashboard?success=Items successfully added to tab!");
                return;

            } catch (SQLException e) {
                if (conn != null) { try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
                e.printStackTrace();
                response.sendRedirect("dashboard?error=Error adding to bill: " + e.getMessage());
                return;
            } finally {
                try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        response.sendRedirect("dashboard?error=Invalid parameters provided.");
    }
}