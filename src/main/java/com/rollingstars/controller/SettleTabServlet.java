package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SettleTabServlet")
public class SettleTabServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Grab the hidden tabId passed from the view-tab.jsp form
        String tabIdStr = request.getParameter("tabId");

        if (tabIdStr != null && !tabIdStr.trim().isEmpty()) {
            int tabId = Integer.parseInt(tabIdStr);

            // 2. Update the status to 'Closed' (or whatever status your history page looks for)
            String sql = "UPDATE bar_tabs SET status = 'Closed' WHERE id = ?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Bind the ID to the query
                stmt.setInt(1, tabId);
                
                // Execute the update
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Database Error: Failed to settle tab!");
                e.printStackTrace();
            }
        }

        // 3. Instantly redirect the bartender back to the live dashboard
        response.sendRedirect("dashboard");
    }
}