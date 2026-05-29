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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Extract the unique Tab ID from the URL parameters
        String idParam = request.getParameter("id");
        
        if (idParam != null) {
            int tabId = Integer.parseInt(idParam);
         // Replaces the old DELETE query string
            String sql = "UPDATE bar_tabs SET status = 'SETTLED' WHERE id = ?";

            // 2. Open the database pipeline and execute the removal
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, tabId);
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 3. Smoothly redirect back to the dashboard to show the updated active listings
        response.sendRedirect("dashboard");
    }
}