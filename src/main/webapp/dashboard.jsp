<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.rollingstars.model.BarTab" %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rolling Stars - Operations Dashboard</title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container my-5">
        
        <div class="row align-items-center mb-4">
            <div class="col-md-6 text-start">
                <h1 class="fw-bold mb-1 text-white">Active Lounge Tabs</h1>
                <p class="text-secondary small m-0">Manage current floor tables and open client running tabs.</p>
            </div>
            <div class="col-md-6 text-md-end text-start mt-3 mt-md-0">
                <a href="add-tab.jsp" class="btn btn-warning fw-bold px-4 py-2 shadow-sm shadow">
                    <i class="bi bi-plus-lg me-1"></i> Open New Session Tab
                </a>
            </div>
        </div>

        <div class="card border-0 bg-dark shadow rounded-3 text-white overflow-hidden" style="background-color: #252538 !important; border: 1px solid #34344d !important;">
            <div class="table-responsive">
                <table class="table table-dark table-hover table-striped align-middle mb-0 text-start" style="--bs-table-bg: #252538;">
                    <thead class="table-light text-uppercase text-secondary fw-bold" style="font-size: 0.8rem; letter-spacing: 0.5px;">
                        <tr>
                            <th class="ps-4">Tab Reference ID</th>
                            <th>Customer Name / Table ID</th>
                            <th>Current Bill Amount</th>
                            <th>Status Badge</th>
                            <th class="text-end pe-4">Management Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<BarTab> activeTabs = (List<BarTab>) request.getAttribute("activeTabs");
                            if (activeTabs != null && !activeTabs.isEmpty()) {
                                for (BarTab tab : activeTabs) {
                        %>
                                    <tr>
                                        <td class="fw-semibold text-warning ps-4">#TS-<%= tab.getId() %></td>
                                        
                                        <td class="fw-medium text-white"><%= tab.getGuestName() %></td>
                                        
                                        <td class="text-success fw-bold">UGX <%= String.format("%,d", tab.getTotalBill()) %></td>
                                        
                                        <td>
                                            <span class="badge rounded-pill bg-success bg-opacity-10 text-success border border-success border-opacity-20 px-3 py-1.5 fw-medium" style="font-size: 0.75rem;">
                                                <i class="bi bi-activity me-1"></i> Active Session
                                            </span>
                                        </td>
                                        
                                        <td class="text-end pe-4">
                                            <a href="view-tab?id=<%= tab.getId() %>" class="btn btn-outline-warning btn-sm fw-semibold px-3 py-1.5 me-2 rounded-2">
                                                <i class="bi bi-receipt me-1"></i> Update Order / Bill
                                            </a>
                                        </td>
                                    </tr>
                        <%
                                }
                            } else {
                        %>
                                <tr>
                                    <td colspan="5" class="text-center text-secondary py-5">
                                        <div class="mb-2"><i class="bi bi-inbox fs-2 text-muted"></i></div>
                                        <p class="mb-0 fw-medium fs-6">No running orders or tabs open on the floor right now.</p>
                                        <small class="text-white-50">Click "+ Open New Tab" above to launch a lounge order seat profile session.</small>
                                    </td>
                                </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

</body>
</html>