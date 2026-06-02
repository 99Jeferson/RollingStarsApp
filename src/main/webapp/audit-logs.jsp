<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rolling Stars - Executive Audit Vault</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/font/bootstrap-icons.css">
    <style>
        body { background-color: #0d0d13; color: #e2e2e9; }
        .card-custom { background-color: #161622; border: 1px solid #28283a; }
        .table-custom th { background-color: #212133; color: #9a9ab0; }
    </style>
</head>
<body>

<div class="container my-5">
    <div class="mb-4 border-bottom pb-3 border-secondary">
        <h2 class="text-danger fw-bold"><i class="bi bi-shield-lock-fill me-2"></i>System Operations Audit Chamber</h2>
        <p class="text-secondary small mb-0">Track system actions by setting date boundaries or filtering by specific employees.</p>
    </div>

    <div class="card card-custom p-4 mb-4">
        <form action="boss-audit" method="GET" class="row g-3 align-items-end">
            <div class="col-md-3">
                <label class="form-label text-secondary small fw-bold">From Date</label>
                <input type="date" name="startDate" class="form-control bg-dark text-white border-secondary" value="${startDate}">
            </div>
            <div class="col-md-3">
                <label class="form-label text-secondary small fw-bold">To Date</label>
                <input type="date" name="endDate" class="form-control bg-dark text-white border-secondary" value="${endDate}">
            </div>
            <div class="col-md-3">
                <label class="form-label text-secondary small fw-bold">Responsible Staff Member</label>
                <select name="worker" class="form-select bg-dark text-white border-secondary">
                    <option value="">-- All Workers --</option>
                    <% 
                        List<String> workers = (List<String>) request.getAttribute("workers");
                        String selectedWorker = (String) request.getAttribute("selectedWorker");
                        if(workers != null) {
                            for(String w : workers) {
                                String selectedAttr = (w.equals(selectedWorker)) ? "selected" : "";
                    %>
                        <option value="<%= w %>" <%= selectedAttr %>><%= w %></option>
                    <% 
                            }
                        }
                    %>
                </select>
            </div>
            <div class="col-md-3 d-grid">
                <button type="submit" class="btn btn-danger fw-bold"><i class="bi bi-search me-1"></i> Scan Audit Logs</button>
            </div>
        </form>
    </div>

    <div class="card card-custom p-3">
        <div class="table-responsive">
            <table class="table table-dark table-hover mb-0">
                <thead>
                    <tr>
                        <th>Log ID</th>
                        <th>Execution Time</th>
                        <th>Responsible Party</th>
                        <th>Operation Type</th>
                        <th>Affected Item</th>
                        <th class="text-center">Qty Shift</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Map<String, Object>> logs = (List<Map<String, Object>>) request.getAttribute("logs");
                        if (logs == null || logs.isEmpty()) {
                    %>
                        <tr><td colspan="6" class="text-center text-secondary py-4">No security logs recorded for this selection.</td></tr>
                    <% 
                        } else {
                            for(Map<String, Object> log : logs) {
                    %>
                        <tr>
                            <td>#<%= log.get("id") %></td>
                            <td class="text-secondary small"><%= log.get("time") %></td>
                            <td class="text-info fw-semibold"><i class="bi bi-person-badge me-1"></i><%= log.get("by") %></td>
                            <td>
                                <span class="badge <%= "SALE_DEDUCTION".equals(log.get("type")) ? "bg-warning text-dark" : "bg-success" %>">
                                    <%= log.get("type") %>
                                </span>
                            </td>
                            <td class="text-white"><%= log.get("itemName") %></td>
                            <td class="text-center fw-bold"><%= log.get("qty") %></td>
                        </tr>
                    <% 
                            }
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>