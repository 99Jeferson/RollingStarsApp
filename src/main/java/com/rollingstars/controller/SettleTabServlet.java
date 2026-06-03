package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.rollingstars.model.User;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SettleTabServlet")
public class SettleTabServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DYNAMIC RESOLUTION: Core user alignment logic
        String activeUser = "System_Staff";
        HttpSession session = request.getSession(false);
        if (session != null) {
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser != null) {
                activeUser = currentUser.getUsername();
            }
        }

        String tabIdStr = request.getParameter("tabId");

        if (tabIdStr != null && !tabIdStr.trim().isEmpty()) {
            int tabId = Integer.parseInt(tabIdStr);
            Connection conn = null;

            try {
                conn = DBConnection.getConnection();
                conn.setAutoCommit(false);

                // 1. Shift customer tab visibility out of active tracker metrics room
                String settleSql = "UPDATE bar_tabs SET status = 'SETTLED', settled_at = NOW() WHERE id = ? AND status = 'ACTIVE'";
                try (PreparedStatement settleStmt = conn.prepareStatement(settleSql)) {
                    settleStmt.setInt(1, tabId);
                    int updatedRows = settleStmt.executeUpdate();
                    if (updatedRows == 0) {
                        throw new SQLException("Tab is either not active or could not be found.");
                    }
                }

                // 2. Insert audit trail with transaction type 'BILL_SETTLEMENT' mapped to activeUser name
                String logSql = "INSERT INTO inventory_logs (item_id, quantity, transaction_type, performed_by) VALUES (NULL, 0, 'BILL_SETTLEMENT', ?)";
                try (PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                    logStmt.setString(1, activeUser);
                    logStmt.executeUpdate();
                }

                conn.commit();
                response.sendRedirect("dashboard?success=Tab successfully closed out and archived!");
                return;

            } catch (SQLException e) {
                if (conn != null) { try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
                e.printStackTrace();
                response.sendRedirect("dashboard?error=Settlement aborted: " + e.getMessage());
                return;
            } finally {
                try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        response.sendRedirect("dashboard?error=Invalid settlement configuration criteria.");
    }
}