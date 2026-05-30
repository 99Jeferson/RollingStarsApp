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

// This mapping catches the request for the history page
@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BarTab> closedTabs = new ArrayList<>();
        
        // Fetch only closed tabs, ordering by most recently closed first (assuming higher ID = more recent)
        String sql = "SELECT * FROM bar_tabs WHERE status = 'Closed' ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BarTab tab = new BarTab();
                tab.setId(rs.getInt("id"));
                tab.setGuestName(rs.getString("guest_name"));
                tab.setTotalBill(rs.getInt("total_bill"));
                tab.setCreatedAt(rs.getTimestamp("created_at"));
                tab.setStatus(rs.getString("status"));
                
                closedTabs.add(tab);
            }
        } catch (SQLException e) {
            System.out.println("Database Error fetching history!");
            e.printStackTrace();
        }

        // Hand the data to the JSP page using the exact key "closedTabs"
        request.setAttribute("closedTabs", closedTabs);
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }
}