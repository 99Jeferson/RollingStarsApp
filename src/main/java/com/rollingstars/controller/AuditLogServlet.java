package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// FIXED: This allows BOTH /audit-logs and /boss-audit to safely load this servlet!
@WebServlet({"/audit-logs", "/boss-audit"}) 
public class AuditLogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // ... rest of your doGet code remains exactly the same as before ...

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String worker = request.getParameter("worker");

        List<Map<String, Object>> auditLogs = new ArrayList<>();
        List<String> workersList = new ArrayList<>();

        // 1. Core query to populate the unique filter dropdown options dynamically
        String workerFilterSql = "SELECT DISTINCT performed_by FROM inventory_logs WHERE performed_by IS NOT NULL ORDER BY performed_by ASC";
        
        // 2. Base SQL layout definition for the log report
     // CHANGE THIS BLOCK INSIDE AuditLogServlet.java:
        StringBuilder sql = new StringBuilder(
            "SELECT l.id, i.item_name, l.quantity, l.transaction_type, l.performed_by, l.created_at " +
            "FROM inventory_logs l " +
            "LEFT JOIN inventory i ON l.item_id = i.id WHERE 1=1" // Changed to LEFT JOIN
        );
        // Evaluation state triggers
        boolean hasDates = (startDate != null && !startDate.trim().isEmpty()) && (endDate != null && !endDate.trim().isEmpty());
        boolean hasWorker = (worker != null && !worker.trim().isEmpty());

        if (hasDates) sql.append(" AND l.created_at BETWEEN ? AND ?");
        if (hasWorker) sql.append(" AND l.performed_by = ?");
        
        sql.append(" ORDER BY l.created_at DESC");

        try (Connection conn = DBConnection.getConnection()) {
            
            // Task A: Fetch active workforce tags to build selection index drop lists
            try (PreparedStatement workerStmt = conn.prepareStatement(workerFilterSql);
                 ResultSet wrkRs = workerStmt.executeQuery()) {
                while (wrkRs.next()) {
                    workersList.add(wrkRs.getString("performed_by"));
                }
            }

            // Task B: Execute filtered log fetch
            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                int paramIdx = 1;

                if (hasDates) {
                    stmt.setString(paramIdx++, startDate + " 00:00:00");
                    stmt.setString(paramIdx++, endDate + " 23:59:59");
                }
                if (hasWorker) {
                    stmt.setString(paramIdx++, worker);
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> log = new HashMap<>();
                        log.put("id", rs.getInt("id"));
                        log.put("itemName", rs.getString("item_name"));
                        log.put("qty", rs.getInt("quantity"));
                        log.put("type", rs.getString("transaction_type"));
                        log.put("by", rs.getString("performed_by"));
                        log.put("time", rs.getTimestamp("created_at"));
                        auditLogs.add(log);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("logs", auditLogs);
        request.setAttribute("workers", workersList);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("selectedWorker", worker);

        request.getRequestDispatcher("audit-logs.jsp").forward(request, response);
    }
}