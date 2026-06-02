package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rollingstars.model.InventoryLog;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/audit-logs")
public class AuditLogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<InventoryLog> auditTrail = new ArrayList<>();
        
        // SQL query joining logs with inventory to get item names, sorted by newest entries first
        String sql = "SELECT l.id, i.item_name, l.quantity, l.transaction_type, l.performed_by, l.logged_at " +
                     "FROM inventory_logs l " +
                     "INNER JOIN inventory i ON l.item_id = i.id " +
                     "ORDER BY l.id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InventoryLog log = new InventoryLog();
                log.setId(rs.getInt("id"));
                log.setItemName(rs.getString("item_name"));
                log.setQuantity(rs.getInt("quantity"));
                log.setTransactionType(rs.getString("transaction_type"));
                log.setPerformedBy(rs.getString("performed_by"));
                log.setLoggedAt(rs.getTimestamp("logged_at"));
                auditTrail.add(log);
            }
        } catch (SQLException e) {
            System.out.println("Database Error loading master audit logs!");
            e.printStackTrace();
        }

        // Forward data to the Boss's interface view
        request.setAttribute("auditTrail", auditTrail);
        request.getRequestDispatcher("audit-logs.jsp").forward(request, response);
    }
}