<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
    <style>
        body { font-family: sans-serif; padding: 40px; background-color: #f4f4f9; }
        .box { background: white; padding: 30px; max-width: 400px; margin: 0 auto; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); color: #333;}
    </style>
</head>
<body>

    <div class="box">
    <%
        // Check if there is actually a guest name in the session memory
        String currentGuest = (String) session.getAttribute("activeGuest");
        
        if (currentGuest == null) {
    %>
            <h3>⚠️ Access Denied</h3>
            <p>You do not have an active session. Please open a tab first.</p>
            <a href="open-tab.jsp">Go to Open a Tab</a>
    <%
        } else {
    %>
            <h3>Order Audit for: <%= currentGuest %></h3>
            <ul>
                <li>1x Nile Special (Pending)</li>
                <li>1x Roasted Goat Chops (Preparing)</li>
            </ul>
            <a href="lounge-lounge.jsp">← Return to Dashboard</a>
    <%
        }
    %>
</div>

</body>
</html>