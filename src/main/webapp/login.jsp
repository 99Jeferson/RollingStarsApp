<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rolling Stars Lounge - Staff Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #1a1a24;
        }
        .card-custom {
            background-color: #252538;
            border: 1px solid #34344d;
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center vh-100">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-12 col-sm-10 col-md-7 col-lg-5 col-xl-4">
            
            <div class="card card-custom text-white shadow-lg rounded-3 p-4">
                <div class="card-body text-center">
                    
                    <h2 class="text-warning fw-bold mb-1">
                        <i class="bi bi-star-fill me-2"></i>Rolling Stars
                    </h2>
                    <p class="text-secondary small mb-4">Staff Portal Authentication</p>

                    <% if (request.getAttribute("errorMessage") != null) { %>
                        <div class="alert alert-danger d-flex align-items-center justify-content-center gap-2 py-2 fs-6 fw-semibold mb-4" role="alert">
                            <i class="bi bi-exclamation-triangle-fill"></i>
                            <span><%= request.getAttribute("errorMessage") %></span>
                        </div>
                    <% } %>

                    <form action="LoginServlet" method="POST" class="text-start">
                        
                        <div class="mb-3">
                            <label class="form-label text-secondary small fw-medium">Username</label>
                            <div class="input-group">
                                <span class="input-group-text bg-dark border-secondary text-secondary"><i class="bi bi-person-fill"></i></span>
                                <input type="text" name="username" class="form-control bg-dark border-secondary text-white shadow-none" placeholder="Enter staff username" required>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label text-secondary small fw-medium">Password</label>
                            <div class="input-group">
                                <span class="input-group-text bg-dark border-secondary text-secondary"><i class="bi bi-lock-fill"></i></span>
                                <input type="password" name="password" class="form-control bg-dark border-secondary text-white shadow-none" placeholder="Enter security password" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-warning w-100 fw-bold py-2.5 mt-2 transition-all">
                            Verify Credentials <i class="bi bi-shield-check ms-1"></i>
                        </button>
                    </form>
                    
                </div>
            </div>
            
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>