<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rollingstars.model.User" %>
<%
    // Safely extract the active user object from the session container
    User userProfile = (User) session.getAttribute("currentUser");
    String staffName = (userProfile != null) ? userProfile.getUsername() : "Staff";
    String staffRole = (userProfile != null) ? userProfile.getRole() : "";
%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

<style>
    body {
        background-color: #1a1a24;
        color: #fff;
    }
    .navbar-custom {
        background-color: #252538;
        border-bottom: 2px solid #34344d;
    }
    .nav-link {
        transition: color 0.2s ease-in-out;
    }
    .nav-link:hover {
        color: #ffcc00 !important;
    }
</style>

<nav class="navbar navbar-expand-lg navbar-dark navbar-custom mb-4 shadow-sm">
    <div class="container">
        
        <a class="navbar-brand text-warning fw-bold fs-4" href="dashboard">
            <i class="bi bi-star-fill me-2"></i>Rolling Stars
        </a>
        
        <button class="navbar-toggler border-0 shadow-none" type="button" data-bs-toggle="collapse" data-bs-target="#loungeNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="loungeNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 fw-medium">
                <li class="nav-item">
                    <a class="nav-link" href="history">Sales History</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-success fw-bold" href="add-tab.jsp">
                        <i class="bi bi-plus-circle-fill me-1"></i> Open New Tab
                    </a>
                </li>
                
                <% if (userProfile != null && !"BARTENDER".equals(staffRole)) { %>
                    <li class="nav-item">
                        <a class="nav-link text-white-50" href="history">
                            <i class="bi bi-graph-up-arrow me-1"></i> Sales History
                        </a>
                    </li>
                <% } %>
            </ul>

            <div class="d-flex align-items-center gap-3">
                <div class="text-end d-none d-sm-block">
                    <div class="small text-white fw-semibold"><%= staffName %></div>
                    <span class="badge bg-dark text-warning border border-secondary px-2 py-1 uppercase" style="font-size: 0.75rem; letter-spacing: 0.5px;">
                        <%= staffRole %>
                    </span>
                </div>
                
                <a href="LogoutServlet" class="btn btn-outline-danger btn-sm fw-bold px-3 py-1.5 shadow-sm">
                    Logout <i class="bi bi-box-arrow-right ms-1"></i>
                </a>
            </div>
        </div>
        
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>