package com.ucu.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Fetch the user's active session
        HttpSession session = request.getSession();
        
        // 2. Extract the price string parameter submitted by the button form
        String priceText = request.getParameter("drinkPrice");
        
        // 3. DATA CONVERSION: Parse the text String into a real mathematical integer primitive
        int numericDrinkPrice = Integer.parseInt(priceText);
        
        // 4. Retrieve the current running total balance from the session vault
        int currentTotalBill = (Integer) session.getAttribute("barBill");
        
        // 5. MATHEMATICAL MATH: Accumulate the old balance with the new drink cost
        int updatedTotalBill = currentTotalBill + numericDrinkPrice;
        
        // 6. Overwrite the old session attribute with our freshly calculated total
        session.setAttribute("barBill", updatedTotalBill);
        
        // 7. Refresh the dashboard screen to show the new live balance
        response.sendRedirect("lounge-lounge.jsp");
    }
}