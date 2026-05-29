<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars - Add to Bill</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #1a1a24; color: #fff; padding: 40px; }
        .container { max-width: 400px; margin: 0 auto; background: #252538; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); }
        h2 { text-align: center; color: #ffcc00; margin-bottom: 5px; }
        .guest-highlight { text-align: center; color: #2ecc71; font-size: 18px; margin-bottom: 25px; font-weight: bold; }
        label { display: block; margin: 10px 0 5px; color: #bbb; }
        input[type="number"] { width: 100%; padding: 10px; margin-bottom: 20px; border: 1px solid #444; border-radius: 6px; background: #111; color: #fff; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background: #ffcc00; border: none; color: #111; font-weight: bold; font-size: 16px; border-radius: 6px; cursor: pointer; transition: 0.3s; }
        button:hover { background: #e6b800; }
        .back-link { display: block; text-align: center; margin-top: 15px; color: #bbb; text-decoration: none; font-size: 14px; }
        .back-link:hover { color: #fff; }
    </style>
</head>
<body>

<div class="container">
    <h2>Add to Bill</h2>
    <div class="guest-highlight">
        <%= request.getParameter("name") %>
    </div>
    
    <form action="UpdateBillServlet" method="POST">
        <input type="hidden" name="tabId" value="<%= request.getParameter("id") %>">
        
        <label>Additional Items Cost (UGX):</label>
        <input type="number" name="additionalAmount" placeholder="e.g., 15000" required min="1">
        
        <button type="submit">Update Total Bill</button>
    </form>
    
    <a href="dashboard" class="back-link">← Cancel and Go Back</a>
</div>

</body>
</html>