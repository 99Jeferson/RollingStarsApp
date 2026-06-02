<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rollingstars.model.InventoryLog" %>
<%@ page import="java.util.List" %>
<%
    List<InventoryLog> auditTrail = (List<InventoryLog>) request.getAttribute("auditTrail");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rolling Stars - System Management Audit</title>
</head>
<body style="background-color: #1a1a24; color: #ffffff;">

    <jsp:include page="header.jsp" />

    <div class="container my-5 text-start">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold text-info m-0"><i class="bi bi-shield-check me-2"></i>Master Security Audit Ledger</h2>
                <p class="text-secondary small m-0">Executive Level Oversight — Un-editable log history tracking stock adjustments.</p>
            </div>
            <a href="dashboard" class="btn btn-outline-info btn-sm">
                <i class="bi bi-speedometer2 me-1"></i> Dashboard
            </a>
        </div>

        <div class="card shadow rounded-3 border-0 text-white" style="background-color: #252538; border: 1px solid #34344d !important;">
            <div class="table-responsive">
                <table class="table table-dark table-hover align-middle mb-0" style="--bs-table-bg: transparent; --bs-table-hover-bg: #2d2d42;">
                    <thead class="text-secondary small text-uppercase" style="border-bottom: 2px solid #34344d;">
                        <tr>
                            <th class="ps-4 py-3">Log ID</th>
                            <th class="py-3">Timestamp</th>
                            <th class="py-3">Action Type</th>
                            <th class="py-3">Item Name</th>
                            <th class="py-3 text-center">Qty Balance Change</th>
                            <th class="pe-4 py-3 text-end">Performed By</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (auditTrail != null && !auditTrail.isEmpty()) { 
                            for (InventoryLog log : auditTrail) { 
                                boolean isRestock = "STOCK_IN".equals(log.getTransactionType());
                                String typeBadgeClass = isRestock ? "bg-success bg-opacity-10 text-success border border-success border-opacity-25" : "bg-danger bg-opacity-10 text-danger border border-danger border-opacity-25";
                                String typeText = isRestock ? "STOCK ARRIED" : "CUSTOMER SALE";
                                String quantitySign = isRestock ? "+" : "-";
                        %>
                            <tr style="border-bottom: 1px solid #34344d;">
                                <td class="ps-4 text-secondary small py-3">#LOG-<%= log.getId() %></td>
                                <td class="text-white-50 small"><%= log.getLoggedAt() %></td>
                                <td>
                                    <span class="badge rounded-2 px-2.5 py-1.5 small <%= typeBadgeClass %>"><%= typeText %></span>
                                </td>
                                <td class="fw-medium text-white"><%= log.getItemName() %></td>
                                <td class="text-center fw-bold <%= isRestock ? "text-success" : "text-danger" %>">
                                    <%= quantitySign %><%= log.getQuantity() %>
                                </td>
                                <td class="pe-4 text-end text-white-50 fw-medium"><%= log.getPerformedBy() %></td>
                            </tr>
                        <%   } 
                           } else { %>
                            <tr>
                                <td colspan="6" class="text-center py-5 text-secondary">No recorded history transactions verified in the system logs.</td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>