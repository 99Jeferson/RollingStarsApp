<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars Lounge - Staff Login</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #1a1a24; color: #fff; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-card { width: 100%; max-width: 400px; background: #252538; padding: 40px; border-radius: 12px; box-shadow: 0 8px 25px rgba(0,0,0,0.6); text-align: center; }
        h2 { color: #ffcc00; margin-bottom: 5px; font-size: 26px; }
        p { color: #bbb; font-size: 14px; margin-bottom: 30px; }
        label { display: block; text-align: left; margin: 15px 0 5px; color: #bbb; font-weight: 500; }
        input[type="text"], input[type="password"] { width: 100%; padding: 12px; border: 1px solid #444; border-radius: 6px; background: #111; color: #fff; box-sizing: border-box; font-size: 15px; }
        input:focus { border-color: #ffcc00; outline: none; }
        .error-msg { color: #e74c3c; background: rgba(231, 76, 60, 0.1); padding: 10px; border-radius: 6px; font-size: 14px; margin-bottom: 20px; font-weight: bold; }
        button { width: 100%; padding: 14px; background: #ffcc00; border: none; color: #111; font-weight: bold; font-size: 16px; border-radius: 6px; cursor: pointer; transition: 0.3s; margin-top: 25px; }
        button:hover { background: #e6b800; }
    </style>
</head>
<body>

<div class="login-card">
    <h2>🌟 Rolling Stars</h2>
    <p>Staff Portal Authentication</p>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error-msg"><%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <form action="LoginServlet" method="POST">
        <label>Username</label>
        <input type="text" name="username" placeholder="Enter your staff username" required>

        <label>Password</label>
        <input type="password" name="password" placeholder="Enter security password" required>

        <button type="submit">Verify Credentials</button>
    </form>
</div>

</body>
</html>