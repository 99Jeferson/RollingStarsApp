package com.ucu.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// This matches the 'action' attribute in our form tag
@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
 // Add this method inside your ReservationServlet class
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If a user tries to access this page directly via URL, send them back to the form
        response.sendRedirect("reserve.jsp");
    }

    // Notice we are using doPost here instead of doGet because the form method is POST
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Extract the data submitted by the HTML form using the 'name' attributes
        String name = request.getParameter("customerName");
        String guests = request.getParameter("guestCount");
        
        // 2. Perform any background business logic (e.g., creating a confirmation message)
        String confirmationMessage = "Cheers, " + name + "! Your VIP table for " + guests + " guests is locked in.";
        
        // 3. Pass the result message forward
        request.setAttribute("message", confirmationMessage);
        
        // 4. Load the confirmation screen layout
        request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }
}