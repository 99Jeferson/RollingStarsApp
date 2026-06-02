<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rollingstars.model.BarTab" %> 
<%@ page import="com.rollingstars.model.InventoryItem" %>
<%@ page import="java.util.List" %>
<%
    // Safely extract data packages arriving out of DashboardServlet
    List<BarTab> activeTabs = (List<BarTab>) request.getAttribute("activeTabs");
    List<InventoryItem> inventoryList = (List<InventoryItem>) request.getAttribute("inventoryList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rolling Stars - Operations Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css">
</head>
<body style="background-color: #1a1a24; color: #ffffff;">

    <jsp:include page="header.jsp" />

    <div class="container my-5">
        
        <div class="row align-items-center mb-4">
            <div class="col-md-6 text-start">
                <h1 class="fw-bold mb-1 text-white">Active Lounge Tabs</h1>
                <p class="text-secondary small m-0">Manage current floor tables and open client running tabs.</p>
            </div>
            <div class="col-md-6 text-md-end text-start mt-3 mt-md-0">
                <button type="button" class="btn btn-warning fw-bold px-4 py-2 shadow" data-bs-toggle="modal" data-bs-target="#openTabModal">
                    <i class="bi bi-plus-lg me-1"></i> Open New Session Tab
                </button>
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
                        <% if (activeTabs != null && !activeTabs.isEmpty()) {
                                for (BarTab tab : activeTabs) { %>
                                    <tr style="border-bottom: 1px solid #34344d;">
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
                        <%     }
                            } else { %>
                                <tr>
                                    <td colspan="5" class="text-center text-secondary py-5">
                                        <div class="mb-2"><i class="bi bi-inbox fs-2 text-muted"></i></div>
                                        <p class="mb-0 fw-medium fs-6">No running orders or tabs open on the floor right now.</p>
                                        <small class="text-white-50">Click "+ Open New Tab" above to launch a lounge order seat profile session.</small>
                                    </td>
                                </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="openTabModal" data-bs-backdrop="static" tabindex="-1" aria-labelledby="openTabModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content text-white border-0" style="background-color: #252538; border: 1px solid #34344d !important;">
                
                <div class="modal-header border-bottom border-secondary border-opacity-25 p-4">
                    <h5 class="modal-title fw-bold text-warning" id="openTabModalLabel">
                        <i class="bi bi-patch-plus me-2"></i>Initialize New Tab Profile
                    </h5>
                    <button type="button" class="btn-close btn-close-white shadow-none" data-bs-dash="modal" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                
                <div class="modal-body p-4 text-start">
                    <form action="CreateTabServlet" method="POST">
                        
                        <div class="mb-3">
                            <label class="form-label text-secondary small fw-medium">Guest Identity / Table Reference</label>
                            <input type="text" name="guestName" class="form-control bg-dark border-secondary text-white shadow-none" 
                                   placeholder="e.g., Table 4 / Club Member" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label text-secondary small fw-medium">First Item Ordered</label>
                            <select name="itemId" class="form-select bg-dark border-secondary text-white shadow-none" required>
                                <option value="" disabled selected>-- Choose Initial Drink / Food --</option>
                                <% if (inventoryList != null) { 
                                    for (InventoryItem item : inventoryList) { %>
                                        <option value="<%= item.getId() %>">
                                            <%= item.getItemName() %> — UGX <%= String.format("%,d", item.getUnitPrice()) %> (Stock: <%= item.getStockQty() %>)
                                        </option>
                                <%   } 
                                   } %>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label class="form-label text-secondary small fw-medium">Quantity Ordered</label>
                            <input type="number" name="quantity" class="form-control bg-dark border-secondary text-white shadow-none" 
                                   min="1" value="1" required>
                        </div>

                        <div class="d-flex gap-2 pt-2">
                            <button type="button" class="btn btn-outline-secondary text-white w-50 fw-semibold" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-warning w-50 fw-bold">Open Active Tab</button>
                        </div>
                    </form>
                </div>
                
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>