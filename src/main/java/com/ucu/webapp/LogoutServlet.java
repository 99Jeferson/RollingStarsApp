package com.ucu.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Fetch the user's current session (false means: don't create a new one if it doesn't exist)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // 2. The Magic Command: This completely clears and destroys the session object
            session.invalidate();
        }
        
        // 3. Kick the user back out to the main entry page
        response.sendRedirect("open-tab.jsp");
    }
}