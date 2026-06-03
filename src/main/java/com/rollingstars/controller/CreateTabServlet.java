package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.rollingstars.model.User;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CreateTabServlet")
public class CreateTabServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DYNAMIC RESOLUTION: Pull profile details down safely
        String activeUser = "System_Staff";
        HttpSession session = request.getSession(false);
        if (session != null) {
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser != null) {
                activeUser = currentUser.getUsername();
            }
        }

        String guestName = request.getParameter("guestName");
        String itemIdStr = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");

        if (guestName != null && itemIdStr != null && quantityStr != null) {
            int itemId = Integer.parseInt(itemIdStr);
            int quantity = Integer.parseInt(quantityStr);
            
            Connection conn = null;
            int newTabId = -1;

            try {
                conn = DBConnection.getConnection();
                conn.setAutoCommit(false);

                String itemSql = "SELECT price, stock_count FROM inventory WHERE id = ?";
                int initialBill = 0;
                try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                    itemStmt.setInt(1, itemId);
                    try (ResultSet rs = itemStmt.executeQuery()) {
                        if (rs.next() && rs.getInt("stock_count") >= quantity) {
                            initialBill = rs.getInt("price") * quantity;
                        } else {
                            throw new SQLException("Insufficient starting stock available!");
                        }
                    }
                }

                String insertTabSql = "INSERT INTO bar_tabs (guest_name, total_bill, status) VALUES (?, ?, 'ACTIVE')";
                try (PreparedStatement tabStmt = conn.prepareStatement(insertTabSql, Statement.RETURN_GENERATED_KEYS)) {
                    tabStmt.setString(1, guestName);
                    tabStmt.setInt(2, initialBill);
                    tabStmt.executeUpdate();
                    
                    try (ResultSet keys = tabStmt.getGeneratedKeys()) {
                        if (keys.next()) { newTabId = keys.getInt(1); }
                    }
                }

                String deductSql = "UPDATE inventory SET stock_count = stock_count - ? WHERE id = ?";
                try (PreparedStatement deductStmt = conn.prepareStatement(deductSql)) {
                    deductStmt.setInt(1, quantity);
                    deductStmt.setInt(2, itemId);
                    deductStmt.executeUpdate();
                }

                // FIXED: Dynamically passes the logged-in staff member into the audit line
                String logSql = "INSERT INTO inventory_logs (item_id, quantity, transaction_type, performed_by) VALUES (?, ?, 'SALE_DEDUCTION', ?)";
                try (PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                    logStmt.setInt(1, itemId);
                    logStmt.setInt(2, quantity);
                    logStmt.setString(3, activeUser);
                    logStmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                if (conn != null) { try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
                e.printStackTrace();
            } finally {
                try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }

            if (newTabId != -1) {
                response.sendRedirect("dashboard?success=New guest session opened successfully!");
                return;
            }
        }
        response.sendRedirect("dashboard?error=Failed to process registration parameters.");
    }
}