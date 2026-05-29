package com.rollingstars.controller;

import com.rollingstars.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddTabServlet")
public class AddTabServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Extract parameters sent from the JSP form
        String guestName = request.getParameter("guestName");
        String billString = request.getParameter("totalBill");
        int totalBill = Integer.parseInt(billString);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 2. Prepare the parameterized SQL query (prevents SQL Injection!)
        String sql = "INSERT INTO bar_tabs (guest_name, total_bill) VALUES (?, ?)";

        // 3. Open the database channel using your automated try-with-resources block
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Bind the values into the placeholders (?)
            stmt.setString(1, guestName);
            stmt.setInt(2, totalBill);

            // Execute the insert statement
            int rowsInserted = stmt.executeUpdate();

            // 4. Send a success message back to the browser
            if (rowsInserted > 0) {
                out.println("<html><body style='background-color:#1a1a24; color:white; font-family:sans-serif; text-align:center; padding-top:50px;'>");
                out.println("<h2 style='color:#2ecc71;'>🎉 Tab Saved Successfully!</h2>");
                out.println("<p>Guest <strong>" + guestName + "</strong> has been permanently written to MySQL.</p>");
                out.println("<a href='add-tab.jsp' style='color:#ffcc00; text-decoration:none;'>← Add Another Tab</a>");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error saving data to database: " + e.getMessage() + "</h3>");
        }
    }
}