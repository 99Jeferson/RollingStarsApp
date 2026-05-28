<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Confirmed</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; padding: 40px; text-align: center; }
        .card { background: white; padding: 40px; max-width: 500px; margin: 0 auto; border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); border-top: 5px solid #2ecc71; }
        h1 { color: #2ecc71; }
        p { font-size: 18px; color: #34495e; }
        a { display: inline-block; margin-top: 20px; color: #3498db; text-decoration: none; }
    </style>
</head>
<body>

    <div class="card">
        <h1>Booking Successful!</h1>
        
        <p><%= request.getAttribute("message") %></p>
        
        <a href="reserve.jsp">← Back to Booking</a>
    </div>

</body>
</html>