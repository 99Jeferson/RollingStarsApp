package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rollingstars.model.BarTab;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BarTab> closedTabs = new ArrayList<>();
        
        int totalRevenue = 0;
        int avgSpend = 0;
        int totalTabsCount = 0;

        // Query 1: Fetch the individual historical closed records
        String listSql = "SELECT * FROM bar_tabs WHERE status = 'Closed' ORDER BY id DESC";
        
        // Query 2: Calculate live financial metrics from all closed accounts
        String metricsSql = "SELECT SUM(total_bill) AS total_rev, AVG(total_bill) AS avg_spend, COUNT(id) AS tab_count FROM bar_tabs WHERE status = 'Closed'";

        try (Connection conn = DBConnection.getConnection()) {
            
            // 1. Execute metrics aggregation
            try (PreparedStatement metricsStmt = conn.prepareStatement(metricsSql);
                 ResultSet rsMetrics = metricsStmt.executeQuery()) {
                if (rsMetrics.next()) {
                    totalRevenue = rsMetrics.getInt("total_rev");
                    avgSpend = rsMetrics.getInt("avg_spend");
                    totalTabsCount = rsMetrics.getInt("tab_count");
                }
            }

            // 2. Execute table row listing
            try (PreparedStatement listStmt = conn.prepareStatement(listSql);
                 ResultSet rsList = listStmt.executeQuery()) {
                while (rsList.next()) {
                    BarTab tab = new BarTab();
                    tab.setId(rsList.getInt("id"));
                    tab.setGuestName(rsList.getString("guest_name"));
                    tab.setTotalBill(rsList.getInt("total_bill"));
                    tab.setCreatedAt(rsList.getTimestamp("created_at"));
                    tab.setStatus(rsList.getString("status"));
                    closedTabs.add(tab);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database Error processing financial analytics workflow!");
            e.printStackTrace();
        }

        // Send both the raw history data list AND the summary metrics data to the front end JSP
        request.setAttribute("closedTabs", closedTabs);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("avgSpend", avgSpend);
        request.setAttribute("totalTabs", totalTabsCount);

        request.getRequestDispatcher("history.jsp").forward(request, response);
    }
}