package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rollingstars.model.BarTab;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// This exact mapping is what fixes your 404 error!
@WebServlet("/view-tab")
public class ViewTabServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Grab the ID from the URL (e.g., view-tab?id=5)
        String tabIdStr = request.getParameter("id");
        BarTab tab = null;

        if (tabIdStr != null && !tabIdStr.trim().isEmpty()) {
            int tabId = Integer.parseInt(tabIdStr);
            
            // 2. Fetch this specific guest's active bill from MySQL
            String sql = "SELECT * FROM bar_tabs WHERE id = ?"; 

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, tabId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // 3. Populate your Java model object
                        tab = new BarTab();
                        tab.setId(rs.getInt("id"));
                        tab.setGuestName(rs.getString("guest_name"));
                        tab.setTotalBill(rs.getInt("total_bill"));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database Error fetching single tab!");
                e.printStackTrace();
            }
        }

        // 4. Route the user based on whether the data was found
        if (tab != null) {
            // Success: Hand the data to the view-tab.jsp page to display
            request.setAttribute("tab", tab);
            request.getRequestDispatcher("view-tab.jsp").forward(request, response);
        } else {
            // Failure: If the ID was missing or invalid, bounce them back to the dashboard
            response.sendRedirect("dashboard");
        }
    }
}