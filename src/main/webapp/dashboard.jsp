<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.rollingstars.model.BarTab" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars - Management Dashboard</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #1a1a24; color: #fff; padding: 40px; }
        .container { max-width: 900px; margin: 0 auto; }
        .header-area { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        h1 { color: #ffcc00; margin: 0; }
        .btn { padding: 10px 20px; background: #ffcc00; color: #111; font-weight: bold; text-decoration: none; border-radius: 6px; transition: 0.3s; }
        .btn:hover { background: #e6b800; }
        table { width: 100%; border-collapse: collapse; background: #252538; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.4); }
        th, td { padding: 15px; text-align: left; }
        th { background-color: #2f2f48; color: #ffcc00; font-weight: 600; text-transform: uppercase; font-size: 13px; }
        tr { border-bottom: 1px solid #383852; }
        tr:last-child { border: none; }
        tr:hover { background-color: #2c2c42; }
        .bill-amount { font-weight: bold; color: #2ecc71; }
        .empty-message { text-align: center; padding: 40px; color: #bbb; font-style: italic; }
    </style>
</head>
<body>

<div class="container">
    <div class="header-area">
        <h1>Rolling Stars Lounge Dashboard</h1>
        <a href="add-tab.jsp" class="btn">+ Open New Tab</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Tab ID</th>
                <th>Guest Name</th>
                <th>Total Bill (UGX)</th>
                <th>Opened At</th>
                <th>Actions<th>
            </tr>
        </thead>
        <tbody>
            <%
                // Retrieve the attached list from our Servlet controller
                List<BarTab> tabs = (List<BarTab>) request.getAttribute("activeTabs");
                if (tabs != null && !tabs.isEmpty()) {
                    for (BarTab tab : tabs) {
            %>
                        <tr>
                            <td>#<%= tab.getId() %></td>
                            <td><strong><%= tab.getGuestName() %></strong></td>
                            <td class="bill-amount"><%= String.format("%,d", tab.getTotalBill()) %></td>
                            <td><%= tab.getCreatedAt() %></td>
                            <td>
                            <a href="update-bill.jsp?id=<%= tab.getId() %>&name=<%= java.net.URLEncoder.encode(tab.getGuestName(), "UTF-8") %>" 
                               style="color: #ffcc00; text-decoration: none; font-weight: bold;">➕ Add to Bill</a>
                        </td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="4" class="empty-message">No active guest tabs found in the database.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>

</body>
</html>