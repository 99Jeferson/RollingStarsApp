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

@WebServlet("/UpdateBillServlet")
public class UpdateBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Grab values out of the form submission
        int tabId = Integer.parseInt(request.getParameter("tabId"));
        int additionalAmount = Integer.parseInt(request.getParameter("additionalAmount"));

        // 2. Formulate the SQL statement utilizing safe parameter binding
        // Using total_bill = total_bill + ? adds directly to the current value in the database
        String sql = "UPDATE bar_tabs SET total_bill = total_bill + ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind values to placeholders
            stmt.setInt(1, additionalAmount);
            stmt.setInt(2, tabId);

            // Execute the change
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 3. Send the bartender directly back to the dashboard to see the updated figures
        response.sendRedirect("dashboard");
    }
}