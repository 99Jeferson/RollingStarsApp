<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rolling Stars Lounge - Inventory Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/font/bootstrap-icons.css">
    <style>
        body { background-color: #1a1a24; color: #ffffff; }
        .card-custom { background-color: #252538; border: 1px solid #34344d; }
        .table-custom { background-color: #1f1f2e; border-collapse: separate; border-spacing: 0; }
        .table-custom th { background-color: #2b2b3d; color: #a0a0b8; border: none; }
        .table-custom td { border-top: 1px solid #34344d; color: #e0e0e6; vertical-align: middle; }
    </style>
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="text-warning fw-bold"><i class="bi bi-boxes me-2"></i>Stock & Pricing Management</h2>
            <p class="text-secondary small mb-0">Monitor levels, restock supplies, and manage menu drink prices</p>
        </div>
    </div>

    <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-danger py-2 fs-6 fw-semibold mb-4" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i><%= request.getParameter("error") %>
        </div>
    <% } %>
    <% if (request.getParameter("success") != null) { %>
        <div class="alert alert-success py-2 fs-6 fw-semibold mb-4" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i><%= request.getParameter("success") %>
        </div>
    <% } %>

    <div class="card card-custom shadow-lg rounded-3 p-3">
        <div class="table-responsive">
            <table class="table table-custom text-white mb-0">
                <thead>
                    <tr>
                        <th>Item ID</th>
                        <th>Drink Name</th>
                        <th>Category</th>
                        <th class="text-center">Stock Level</th>
                        <th>Unit Price</th>
                        <th>Status</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) request.getAttribute("inventoryData");
                        if (items == null || items.isEmpty()) { 
                    %>
                        <tr>
                            <td colspan="7" class="text-center text-secondary py-4">No inventory data found.</td>
                        </tr>
                    <% 
                        } else {
                            for (Map<String, Object> item : items) { 
                                int stock = (Integer) item.get("stockCount");
                                double price = (Double) item.get("price");
                                String badgeClass = "bg-success";
                                String statusText = "Healthy";

                                if (stock <= 5) {
                                    badgeClass = "bg-danger text-white";
                                    statusText = "Critically Low";
                                } else if (stock < 15) {
                                    badgeClass = "bg-warning text-dark";
                                    statusText = "Low Stock";
                                }
                    %>
                        <tr>
                            <td>#<%= item.get("id") %></td>
                            <td class="fw-semibold text-warning"><%= item.get("itemName") %></td>
                            <td><span class="badge bg-secondary"><%= item.get("category") %></span></td>
                            <td class="text-center fw-bold"><%= stock %> units</td>
                            <td class="text-info fw-semibold">UGX <%= String.format("%,.0f", price) %></td>
                            <td><span class="badge <%= badgeClass %>"><%= statusText %></span></td>
                            <td class="text-center">
                                <button class="btn btn-sm btn-outline-warning me-1" 
                                        onclick="openRestockModal('<%= item.get("id") %>', '<%= item.get("itemName") %>', '<%= stock %>')">
                                    <i class="bi bi-plus-circle me-1"></i> Restock
                                </button>
                                <button class="btn btn-sm btn-outline-info" 
                                        onclick="openPriceModal('<%= item.get("id") %>', '<%= item.get("itemName") %>', '<%= price %>')">
                                    <i class="bi bi-tags me-1"></i> Change Price
                                </button>
                            </td>
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

<div class="modal fade" id="restockModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content bg-dark text-white border-secondary">
            <div class="modal-header border-secondary">
                <h5 class="modal-title text-warning"><i class="bi bi-box-seam me-2"></i>Restock Supply</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="inventory-control" method="POST">
                <input type="hidden" name="action" value="restock">
                <input type="hidden" name="itemId" id="modalItemId">
                
                <div class="modal-body">
                    <p class="mb-2">Item: <span id="modalItemName" class="text-warning fw-bold"></span></p>
                    <p class="text-secondary small mb-3">Current Storage Volume: <span id="modalCurrentStock" class="text-white fw-bold"></span> units</p>
                    <div class="mb-3">
                        <label class="form-label text-secondary small fw-medium">Quantity to Add (Units)</label>
                        <input type="number" name="quantity" class="form-control bg-black text-white border-secondary shadow-none" min="1" required>
                    </div>
                </div>
                <div class="modal-footer border-secondary">
                    <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-warning btn-sm fw-bold">Confirm Add</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="priceModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content bg-dark text-white border-secondary">
            <div class="modal-header border-secondary">
                <h5 class="modal-title text-info"><i class="bi bi-tags me-2"></i>Adjust Rate Price</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="inventory-control" method="POST">
                <input type="hidden" name="action" value="updatePrice">
                <input type="hidden" name="itemId" id="priceItemId">
                
                <div class="modal-body">
                    <p class="mb-2">Item Name: <span id="priceItemName" class="text-warning fw-bold"></span></p>
                    <p class="text-secondary small mb-3">Active Unit Price: UGX <span id="priceCurrentRate" class="text-white fw-bold"></span></p>
                    <div class="mb-3">
                        <label class="form-label text-secondary small fw-medium">New Selling Price (UGX)</label>
                        <input type="number" name="newPrice" class="form-control bg-black text-white border-secondary shadow-none" min="0" placeholder="e.g. 7000" required>
                    </div>
                </div>
                <div class="modal-footer border-secondary">
                    <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-info btn-sm text-dark fw-bold">Update Pricing</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function openRestockModal(id, name, currentStock) {
        document.getElementById('modalItemId').value = id;
        document.getElementById('modalItemName').innerText = name;
        document.getElementById('modalCurrentStock').innerText = currentStock;
        new bootstrap.Modal(document.getElementById('restockModal')).show();
    }

    function openPriceModal(id, name, currentPrice) {
        document.getElementById('priceItemId').value = id;
        document.getElementById('priceItemName').innerText = name;
        document.getElementById('priceCurrentRate').innerText = Number(currentPrice).toLocaleString();
        new bootstrap.Modal(document.getElementById('priceModal')).show();
    }
</script>
</body>
</html>