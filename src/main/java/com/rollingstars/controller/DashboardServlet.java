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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BarTab> tabList = new ArrayList<>();
        String sql = "SELECT id, guest_name, total_bill, created_at FROM bar_tabs ORDER BY created_at DESC";

        // Query the database using the centralized connection tool
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BarTab tab = new BarTab(
                    rs.getInt("id"),
                    rs.getString("guest_name"),
                    rs.getInt("total_bill"),
                    rs.getTimestamp("created_at")
                );
                tabList.add(tab);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Attach our list of records to the request object so the JSP page can read it
        request.setAttribute("activeTabs", tabList);

        // Forward the control directly over to our front-end view
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}