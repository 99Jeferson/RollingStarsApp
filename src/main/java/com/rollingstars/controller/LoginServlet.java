package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rollingstars.model.User;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameInput = request.getParameter("username");
        String passwordInput = request.getParameter("password");

        String sql = "SELECT id, username, role FROM users WHERE username=? AND password=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usernameInput);
            stmt.setString(2, passwordInput);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HttpSession session = request.getSession();
                    
                    int id = rs.getInt("id");
                    String dbUsername = rs.getString("username");
                    String userRole = rs.getString("role"); 
                    
                    // Creates user object to align directly with header.jsp
                    User activeUser = new User(id, dbUsername, userRole);
                    session.setAttribute("currentUser", activeUser);
                    
                    response.sendRedirect("dashboard");
                    return;
                } else {
                    response.sendRedirect("login.jsp?error=Invalid Credentials");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Prevents a blank screen by sending database errors directly back to the screen UI
            response.sendRedirect("login.jsp?error=Database Error: " + e.getMessage());
        }
    }
}