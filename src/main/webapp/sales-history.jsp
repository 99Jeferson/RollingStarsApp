<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, java.util.Map, java.sql.Timestamp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rolling Stars Lounge - Sales Statements</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/font/bootstrap-icons.css">
    <style>
        body { background-color: #12121a; color: #ffffff; }
        .card-custom { background-color: #1c1c28; border: 1px solid #2d2d3f; }
        
        /* CSS Print Directive Strategy: Strips interface layouts when hard-printing */
        @media print {
            body { background: #ffffff !important; color: #000000 !important; }
            .no-print, form, .btn, header, nav { display: none !important; }
            .card-custom { background: transparent !important; border: none !important; color: #000000 !important; }
            .table { color: #000000 !important; }
            .text-warning, .text-info { color: #000000 !important; font-weight: bold; }
        }
    </style>
</head>
<body>

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3 border-secondary">
        <div>
            <h2 class="text-warning fw-bold"><i class="bi bi-receipt-cutoff me-2"></i>Sales Ledger Statements</h2>
            <p class="text-secondary small mb-0">Review settled guest revenues and extract print configurations.</p>
        </div>
        <button onclick="window.print()" class="btn btn-info btn-sm fw-bold no-print">
            <i class="bi bi-printer me-1"></i> Print Statement Result
        </button>
    </div>

    <div class="card card-custom p-4 mb-4 no-print">
        <form action="sales-history" method="GET" class="row g-3 align-items-end">
            <div class="col-md-4">
                <label class="form-label text-secondary small fw-bold">Settlement Starting Date</label>
                <input type="date" name="startDate" class="form-control bg-dark text-white border-secondary" 
                       value="${startDate != null ? startDate : ''}" required>
            </div>
            <div class="col-md-4">
                <label class="form-label text-secondary small fw-bold">Settlement Closing Date</label>
                <input type="date" name="endDate" class="form-control bg-dark text-white border-secondary" 
                       value="${endDate != null ? endDate : ''}" required>
            </div>
            <div class="col-md-4 d-grid">
                <button type="submit" class="btn btn-warning fw-bold"><i class="bi bi-funnel me-1"></i> Filter Ledger Logs</button>
            </div>
        </form>
    </div>

    <div class="card card-custom p-3 mb-4 text-center">
        <h4 class="mb-0 text-secondary">Aggregate Revenue Total Within Bounds: 
            <span class="text-success fw-bold">UGX <%= String.format("%,.0f", (Double)request.getAttribute("totalRevenue")) %></span>
        </h4>
    </div>

    <div class="card card-custom p-3">
        <table class="table table-dark table-striped mb-0">
            <thead>
                <tr>
                    <th>Invoice ID</th>
                    <th>Customer Guest Designation</th>
                    <th>Settlement Timestamp</th>
                    <th class="text-end">Final Settlement Cost</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    ArrayList<Map<String, Object>> records = (ArrayList<Map<String, Object>>) request.getAttribute("salesData");
                    if (records == null || records.isEmpty()) {
                %>
                    <tr><td colspan="4" class="text-center text-secondary py-4">No matching records found within the specified date range.</td></tr>
                <% 
                    } else {
                        for (Map<String, Object> row : records) {
                %>
                    <tr>
                        <td>#<%= row.get("id") %></td>
                        <td class="text-warning fw-semibold"><%= row.get("guestName") %></td>
                        <td><%= row.get("settledAt") %></td>
                        <td class="text-end text-info fw-bold">UGX <%= String.format("%,.0f", (Double)row.get("totalBill")) %></td>
                    </tr>
                <% 
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>