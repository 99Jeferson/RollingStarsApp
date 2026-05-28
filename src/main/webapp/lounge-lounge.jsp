<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>The Lounge Area</title>
    <style>
        body { font-family: sans-serif; background-color: #2c3e50; color: white; padding: 40px; text-align: center; }
        .dashboard { background: #34495e; padding: 40px; max-width: 500px; margin: 0 auto; border-radius: 12px; }
        .menu-btn { background: #3498db; color: white; border: none; padding: 10px; margin: 5px; border-radius: 4px; cursor: pointer; font-size: 14px; width: 80%; }
        .menu-btn:hover { background: #2980b9; }
        .bill-box { background: #16a085; padding: 15px; border-radius: 6px; font-size: 22px; font-weight: bold; margin: 20px 0; }
    </style>
</head>
<body>

    <div class="dashboard">
        <h1>Status: Active Tab Open 🟢</h1>
        <h2>Current Guest: <%= session.getAttribute("activeGuest") %></h2>
        
        <div class="bill-box">
            Total Bar Bill: UGX <%= session.getAttribute("barBill") %>
        </div>

        <h3>Order a Drink:</h3>
        <form action="OrderServlet" method="POST">
            <input type="hidden" name="drinkPrice" value="5000">
            <button type="submit" class="menu-btn">🍺 Order Nile Special (5,000 UGX)</button>
        </form>
        
        <form action="OrderServlet" method="POST">
            <input type="hidden" name="drinkPrice" value="7000">
            <button type="submit" class="menu-btn">🍹 Order Uganda Waragi & Tonic (7,000 UGX)</button>
        </form>

        <hr style="border-color: #455a64; margin: 30px 0;">
        
        <form action="LogoutServlet" method="POST">
            <button type="submit" style="background: #c0392b; color: white; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; width: 100%; font-size: 16px;">
                💳 Settle & Close Bar Tab
            </button>
        </form>
    </div>

</body>
</html>