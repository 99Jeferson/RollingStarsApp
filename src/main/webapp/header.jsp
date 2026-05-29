<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rollingstars.model.User" %>
<%
    // Safely extract the active user object from the session container
    User userProfile = (User) session.getAttribute("currentUser");
    String staffName = (userProfile != null) ? userProfile.getUsername() : "Staff";
    String staffRole = (userProfile != null) ? userProfile.getRole() : "";
%>

<style>
    /* Global Navigation Styles preserved from your layout */
    .navbar {
        background-color: #252538;
        padding: 15px 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-radius: 8px;
        margin-bottom: 30px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    }
    .navbar-brand {
        color: #ffcc00;
        font-size: 22px;
        font-weight: bold;
        text-decoration: none;
        letter-spacing: 1px;
    }
    .navbar-links {
        display: flex;
        align-items: center;
    }
    .navbar-links a {
        color: #fff;
        text-decoration: none;
        margin-left: 20px;
        font-weight: 500;
        transition: 0.3s;
        padding: 8px 16px;
        border-radius: 4px;
    }
    .navbar-links a:hover {
        background-color: #2f2f48;
        color: #ffcc00;
    }
    .navbar-links a.active-link {
        background-color: #ffcc00;
        color: #111;
        font-weight: bold;
    }
</style>

<div class="navbar">
    <a href="dashboard" class="navbar-brand">🌟 Rolling Stars Lounge</a>
    
    <div class="navbar-links">
        <span style="color: #bbb; margin-right: 15px; font-size: 14px;">
            User: <strong><%= staffName %></strong> 
            <% if(!staffRole.isEmpty()) { %> [<%= staffRole %>] <% } %>
        </span>

        **<a href="dashboard">Dashboard</a>**
        **<a href="add-tab.jsp">+ Open New Tab</a>**
        
        <% if (userProfile != null && !"BARTENDER".equals(staffRole)) { %>
            **<a href="history">📈 Sales History</a>**
        <% } %>
        
        <a href="LogoutServlet" style="color: #ff5555; margin-left: 20px; font-weight: bold; text-decoration: none;">❌ Logout</a>
    </div>
</div>