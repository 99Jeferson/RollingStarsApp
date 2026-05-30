<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.rollingstars.model.BarTab" %>
<%
    // Fetch the active single tab record sent from your ViewTabServlet
    BarTab tab = (BarTab) request.getAttribute("tab");
    if (tab == null) {
        response.sendRedirect("dashboard");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rolling Stars - Manage Tab #<%= tab.getId() %></title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container my-5 text-start">
        <div class="row justify-content-center">
            <div class="col-12 col-lg-8">
                
                <div class="mb-3">
                    <a href="dashboard" class="text-warning text-decoration-none small fw-medium">
                        <i class="bi bi-arrow-left me-1"></i> Return to Live Floor Track
                    </a>
                </div>

                <div class="card shadow rounded-3 text-white mb-4" style="background-color: #252538; border: 1px solid #34344d;">
                    <div class="card-header border-bottom border-secondary bg-dark bg-opacity-25 p-4 d-flex justify-content-between align-items-center">
                        <div>
                            <h3 class="fw-bold m-0 text-warning">
                                <i class="bi bi-receipt-cutoff me-2"></i>Tab Session #TS-<%= tab.getId() %>
                            </h3>
                            <span class="fs-5 text-white-50"><%= tab.getGuestName() %></span>
                        </div>
                        <div class="text-end">
                            <span class="small text-secondary d-block">Current Due</span>
                            <span class="fs-4 fw-bold text-success">UGX <%= String.format("%,d", tab.getTotalBill()) %></span>
                        </div>
                    </div>
                    
                    <div class="card-body p-4">
                        <div class="row g-4">
                            
                            <div class="col-12 col-md-6 border-end border-secondary border-opacity-25">
                                <h5 class="fw-bold text-white mb-3"><i class="bi bi-plus-circle me-2 text-warning"></i>Add to Current Bill</h5>
                                
                                <form action="UpdateBillServlet" method="POST">
                                    <input type="hidden" name="tabId" value="<%= tab.getId() %>">
                                    
                                    <div class="mb-4">
                                        <label class="form-label text-secondary small fw-medium">Additional Charge Amount (UGX)</label>
                                        <div class="input-group">
                                            <span class="input-group-text bg-dark border-secondary text-success fw-bold">UGX</span>
                                            <input type="number" name="additionalAmount" class="form-control bg-dark border-secondary text-white shadow-none" 
                                                   placeholder="e.g., 25000" min="1" required>
                                        </div>
                                        <div class="form-text text-white-50 mt-1" style="font-size: 0.75rem;">
                                            Enter the cost of the new drinks or items served to add them to the total bill.
                                         </div>
                                    </div>

                                    <button type="submit" class="btn btn-warning w-100 fw-bold">
                                        <i class="bi bi-patch-plus me-1"></i> Append to Invoice
                                    </button>
                                </form>
                            </div>

                            <div class="col-12 col-md-6 d-flex flex-column justify-content-between ps-md-4">
                                <div>
                                    <h5 class="fw-bold text-white mb-3"><i class="bi bi-shield-lock me-2 text-danger"></i>Account Settlement</h5>
                                    <p class="text-secondary small">Clicking below will process final payment collections, clear the running profile, and push this session data permanently into the historical sales records archives.</p>
                                </div>
                                
                                <div class="pt-3">
                                    <form action="SettleTabServlet" method="POST" onsubmit="return confirm('Are you sure you want to collect payment and close this tab permanently?');">
                                        <input type="hidden" name="tabId" value="<%= tab.getId() %>">
                                        <button type="submit" class="btn btn-success w-100 fw-bold py-2.5">
                                            <i class="bi bi-check2-all me-2"></i>Settle & Close Tab Account
                                        </button>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

</body>
</html>