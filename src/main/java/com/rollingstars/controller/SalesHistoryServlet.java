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

@WebServlet("/sales-history")
public class SalesHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("startDate"); // format: YYYY-MM-DD
        String endDate = request.getParameter("endDate");     // format: YYYY-MM-DD

        ArrayList<Map<String, Object>> salesList = new ArrayList<>();
        double totalRevenue = 0.0;

        // Base SQL string
        StringBuilder sql = new StringBuilder("SELECT id, guest_name, total_bill, settled_at FROM bar_tabs WHERE status = 'SETTLED'");
        
        boolean hasDates = (startDate != null && !startDate.trim().isEmpty()) && (endDate != null && !endDate.trim().isEmpty());
        if (hasDates) {
            sql.append(" AND settled_at BETWEEN ? AND ?");
        }
        sql.append(" ORDER BY settled_at DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            if (hasDates) {
                // Pad time metadata strings to ensure whole-day coverage spans beautifully
                stmt.setString(1, startDate + " 00:00:00");
                stmt.setString(2, endDate + " 23:59:59");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> record = new HashMap<>();
                    record.put("id", rs.getInt("id"));
                    record.put("guestName", rs.getString("guest_name"));
                    double bill = rs.getDouble("total_bill");
                    record.put("totalBill", bill);
                    record.put("settledAt", rs.getTimestamp("settled_at"));
                    
                    totalRevenue += bill;
                    salesList.add(record);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Send filter state back to JSP so the text inputs stay filled out on reload
        request.setAttribute("salesData", salesList);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        request.getRequestDispatcher("sales-history.jsp").forward(request, response);
    }
}