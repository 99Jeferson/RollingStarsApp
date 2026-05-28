package com.ucu.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Crucial import for session management
import java.io.IOException;

@WebServlet("/TabServlet")
public class TabServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Grab the input string from the form
        String name = request.getParameter("guestName");
        
        // 2. Call the Session vault from Tomcat
        HttpSession session = request.getSession();
        
        // 3. Store the name inside the session vault. It stays here until the browser closes!
        session.setAttribute("activeGuest", name);
        
     // Initialize a running bill total as an Integer primitive wrapper object
        session.setAttribute("barBill", 0);
        
        // 4. Send the user to the active lounge area dashboard
        response.sendRedirect("lounge-lounge.jsp");
    }
}