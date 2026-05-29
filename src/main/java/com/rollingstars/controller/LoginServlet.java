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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameInput = request.getParameter("username");
        String passwordInput = request.getParameter("password");

        // Targeted query searching inside the 'users' table we just fixed
        String sql = "SELECT id, username, role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usernameInput);
            stmt.setString(2, passwordInput);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Match found! Create the user's data object
                    User authenticatedUser = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                    );

                    // Create a session to keep them logged in across the app
                    HttpSession session = request.getSession();
                    session.setAttribute("currentUser", authenticatedUser);

                    // Send them into the operations dashboard
                    response.sendRedirect("dashboard");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If execution falls through here, login failed
        request.setAttribute("errorMessage", "Invalid Username or Security Password!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}