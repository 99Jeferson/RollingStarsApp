<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.rollingstars.model.BarTab" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars - Sales History</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #1a1a24; color: #fff; padding: 40px; }
        .container { max-width: 950px; margin: 0 auto; }
        table { width: 100%; border-collapse: collapse; background: #252538; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.4); margin-top: 20px; }
        th, td { padding: 15px; text-align: left; }
        th { background-color: #2f2f48; color: #ffcc00; font-weight: 600; text-transform: uppercase; font-size: 13px; }
        tr { border-bottom: 1px solid #383852; }
        tr:hover { background-color: #2c2c42; }
        .revenue-amount { font-weight: bold; color: #2ecc71; }
        .total-row { background-color: #2f2f48; font-weight: bold; color: #2ecc71; border-top: 2px solid #2ecc71; }
        .status-badge { background: #27ae60; color: #fff; padding: 4px 10px; border-radius: 20px; font-size: 11px; font-weight: bold; text-transform: uppercase; }
        .empty-message { text-align: center; padding: 40px; color: #bbb; font-style: italic; }
    </style>
</head>
<body>

<div class="container">
    <jsp:include page="header.jsp" />

    <table>
        <thead>
            <tr>
                <th>Tab ID</th>
                <th>Guest Name</th>
                <th>Total Paid (UGX)</th>
                <th>Settled On</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<BarTab> tabs = (List<BarTab>) request.getAttribute("settledTabs");
                int totalRevenue = 0;
                
                if (tabs != null && !tabs.isEmpty()) {
                    for (BarTab tab : tabs) {
                        totalRevenue += tab.getTotalBill();
            %>
                        <tr>
                            <td>#<%= tab.getId() %></td>
                            <td><strong><%= tab.getGuestName() %></strong></td>
                            <td class="revenue-amount"><%= String.format("%,d", tab.getTotalBill()) %></td>
                            <td><%= tab.getCreatedAt() %></td>
                            <td><span class="status-badge"><%= tab.getStatus() %></span></td>
                        </tr>
            <%
                    }
            %>
                    <tr class="total-row">
                        <td colspan="2" style="text-align: right; text-transform: uppercase; color: #fff;">Total Historical Revenue:</td>
                        <td class="revenue-amount" style="font-size: 16px;"><%= String.format("%,d", totalRevenue) %> UGX</td>
                        <td colspan="2"></td>
                    </tr>
            <%
                } else {
            %>
                <tr>
                    <td colspan="5" class="empty-message">No historical settled transactions recorded yet.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>

</body>
</html>