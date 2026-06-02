<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rollingstars.model.InventoryItem" %>
<%@ page import="java.util.List" %>
<%
    List<InventoryItem> fullInventory = (List<InventoryItem>) request.getAttribute("fullInventory");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rolling Stars - Inventory Management</title>
</head>
<body style="background-color: #1a1a24; color: #ffffff;">

    <jsp:include page="header.jsp" />

    <div class="container my-5 text-start">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-warning m-0"><i class="bi bi-boxes me-2"></i>Stockroom Inventory Control</h2>
                <p class="text-secondary small m-0">Authorized Personnel Only — Log incoming vendor supplies and audit reserves.</p>
            </div>
            <a href="dashboard" class="btn btn-outline-warning btn-sm">
                <i class="bi bi-speedometer2 me-1"></i> Dashboard
            </a>
        </div>

        <div class="card shadow rounded-3 border-0 text-white" style="background-color: #252538; border: 1px solid #34344d !important;">
            <div class="table-responsive">
                <table class="table table-dark table-hover align-middle mb-0" style="--bs-table-bg: transparent; --bs-table-hover-bg: #2d2d42;">
                    <thead class="text-secondary small text-uppercase" style="border-bottom: 2px solid #34344d;">
                        <tr>
                            <th class="ps-4 py-3">Item Details</th>
                            <th class="py-3">Unit Rate (UGX)</th>
                            <th class="py-3">Current Stock Balance</th>
                            <th class="py-3">Status Badge</th>
                            <th class="pe-4 py-3 text-center" style="width: 280px;">Log Supply Restock</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (fullInventory != null && !fullInventory.isEmpty()) { 
                            for (InventoryItem item : fullInventory) { 
                                int stock = item.getStockQty();
                                String rowBadgeClass = "bg-success";
                                String badgeText = "Healthy Reserves";
                                
                                if (stock <= 0) {
                                    rowBadgeClass = "bg-danger";
                                    badgeText = "Out of Stock";
                                } else if (stock <= 15) {
                                    rowBadgeClass = "bg-warning text-dark";
                                    badgeText = "Critically Low";
                                }
                        %>
                            <tr style="border-bottom: 1px solid #34344d;">
                                <td class="ps-4 fw-medium text-white py-3"><%= item.getItemName() %></td>
                                <td class="text-white-50">UGX <%= String.format("%,d", item.getUnitPrice()) %></td>
                                <td class="fw-bold"><%= stock %> units</td>
                                <td>
                                    <span class="badge <%= rowBadgeClass %> px-2.5 py-1.5 rounded-2 small"><%= badgeText %></span>
                                </td>
                                <td class="pe-4 py-2">
                                    <form action="RestockInventoryServlet" method="POST" class="d-flex gap-2">
                                        <input type="hidden" name="itemId" value="<%= item.getId() %>">
                                        <input type="number" name="restockQty" class="form-control form-control-sm bg-dark border-secondary text-white text-center shadow-none" 
                                               min="1" placeholder="+ Add Amount" required style="width: 110px;">
                                        <button type="submit" class="btn btn-warning btn-sm fw-bold flex-grow-1">
                                            <i class="bi bi-plus-lg"></i> Restock
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        <%   } 
                           } else { %>
                            <tr>
                                <td colspan="5" class="text-center py-5 text-secondary">No catalog entries detected inside database.</td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>