package com.rollingstars.controller;

import java.io.IOException;
import com.rollingstars.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Protects our primary pages from unauthorized backdoor entries
@WebFilter(urlPatterns = {"/dashboard", "/history", "/add-tab.jsp"})
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Required lifecycle method for Tomcat compilation stability
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Check if an active staff profile session is alive
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (currentUser == null) {
            // No credentials found -> kick them out to the login screen
            httpResponse.sendRedirect("login.jsp");
            return;
        }

        // Role restriction check for financial corporate logs (with explicit null-safety)
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.contains("/history") && currentUser.getRole() != null && "BARTENDER".equals(currentUser.getRole())) {
            // Bartenders are blocked from history and redirected back to the active dashboard
            httpResponse.sendRedirect("dashboard");
            return;
        }

        // Everything looks good. Let the request through.
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Required lifecycle method for clean server teardown
    }
}