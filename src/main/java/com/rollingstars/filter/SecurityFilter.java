package com.rollingstars.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.rollingstars.model.User;

@WebFilter("/*") // This tells Tomcat to route EVERY single request through this security guard first
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization setup needed for this filter
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // Fetch session if it exists; do not create a new one

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // =========================================================================
        // STEP 1: PUBLIC EXEMPTIONS (The Pass-Through Lanes)
        // =========================================================================
        // We must allow anyone to hit the login page, the login processing servlet,
        // and public web assets (CSS, JS, images). Otherwise, they would be locked out forever!
        if (requestURI.endsWith("login.jsp") || 
            requestURI.endsWith("LoginServlet") || 
            requestURI.contains("/css/") || 
            requestURI.contains("/js/") || 
            requestURI.contains("/images/")) {
            
            chain.doFilter(request, response); // Pass the request forward cleanly
            return;
        }

        // =========================================================================
        // STEP 2: MANDATORY AUTHENTICATION CHECK
        // =========================================================================
        // Check if there is an active user object saved in the session.
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
        
        if (currentUser == null) {
            // No session found! Kick them out to the login portal with a warning parameter
            httpResponse.sendRedirect(contextPath + "/login.jsp?error=Please log in to access the system.");
            return;
        }

        // Grab their structural role for the permission checks below
        String staffRole = currentUser.getRole();

        // =========================================================================
        // STEP 3: STOCK MANAGEMENT SECURITY ZONE
        // =========================================================================
        // Only MANAGERS and the BOSS are allowed to see or interact with stock/inventory URLs.
        if (requestURI.contains("inventory-control") || requestURI.contains("inventory.jsp")) {
            if (!"MANAGER".equals(staffRole) && !"BOSS".equals(staffRole)) {
                // If a Bartender tries to force access, trap them and bounce them back to the dashboard
                httpResponse.sendRedirect(contextPath + "/dashboard?error=Access Denied: Manager or Owner clearance required.");
                return;
            }
        }

        // =========================================================================
        // STEP 4: EXECUTIVE AUDIT LOG SECURITY ZONE
        // =========================================================================
        // The transaction ledger is strictly reserved for the owner (BOSS).
        if (requestURI.contains("audit-logs") || requestURI.contains("audit.jsp")) {
            if (!"BOSS".equals(staffRole)) {
                // If a Bartender or Manager tries to sneak in, boot them back to the dashboard
                httpResponse.sendRedirect(contextPath + "/dashboard?error=Access Denied: Executive Owner Clearance Required.");
                return;
            }
        }

        // =========================================================================
        // STEP 5: SECURITY HURDLES CLEARED
        // =========================================================================
        // If the user hasn't triggered any of our traps, they are fully cleared!
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Clean up filter resources if necessary
    }
}