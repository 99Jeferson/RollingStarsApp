package com.ucu.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This maps the browser address bar directly to this Java file
@WebServlet("/Menu")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Create our backend data (The items we want to show)
        List<String> drinksMenu = new ArrayList<>();
        drinksMenu.add("Nile Special");
        drinksMenu.add("Tusker Lite");
        drinksMenu.add("Club Pilsener");
        drinksMenu.add("Uganda Waragi & Tonic");
        drinksMenu.add("Guinness Stout");

        // 2. Attach the list to the Request container object
        // "menuItems" is the name our JSP will use to look up this list
        request.setAttribute("menuItems", drinksMenu);

        // 3. Forward this data straight down the pipeline to our frontend page
        request.getRequestDispatcher("/menu.jsp").forward(request, response);
    }
}