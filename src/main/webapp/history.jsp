<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.rollingstars.model.BarTab" %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rolling Stars Lounge - Financial Logs</title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container my-5">
        
        <div class="row align-items-center mb-4">
            <div class="col-md-8 text-start">
                <h1 class="fw-bold mb-1 text-white">📈 Historical Sales Ledger</h1>
                <p class="text-secondary small m-0">Review closed customer accounts, corporate archives, and historical lounge revenue logs.</p>
            </div>
            <div class="col-md-4 text-md-end text-start mt-3 mt-md-0">
                <button onclick="window.print();" class="btn btn-outline-secondary text-white fw-semibold px-3 py-2 btn-sm rounded-2">
                    <i class="bi bi-printer-fill me-1"></i> Print Statement
                </button>
            </div>
        </div>

        <div class="card border-0 bg-dark shadow rounded-3 text-white overflow-hidden" style="background-color: #252538 !important; border: 1px solid #34344d !important;">
            <div class="table-responsive">
                <table class="table table-dark table-hover table-striped align-middle mb-0 text-start" style="--bs-table-bg: #252538;">
                    <thead class="table-light text-uppercase text-secondary fw-bold" style="font-size: 0.8rem; letter-spacing: 0.5px;">
                        <tr>
                            <th class="ps-4">Archive Ref ID</th>
                            <th>Settled Guest / Account</th>
                            <th>Revenue Collected (UGX)</th>
                            <th>Payment Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // Adjust the attribute string name and casting object to match your specific HistoryServlet
                            List<BarTab> closedTabs = (List<BarTab>) request.getAttribute("closedTabs");
                            if (closedTabs != null && !closedTabs.isEmpty()) {
                                for (BarTab record : closedTabs) {
                        %>
                                    <tr>
                                        <td class="fw-semibold text-warning ps-4">#ARC-<%= record.getId() %></td>
                                        
                                        <td class="fw-medium text-white"><define class="text-white"><%= record.getGuestName() %></define></td>
                                        
                                        <td class="text-success fw-bold">UGX <%= String.format("%,d", record.getTotalBill()) %></td>
                                        
                                        <td>
                                            <span class="badge rounded-pill bg-info bg-opacity-10 text-info border border-info border-opacity-20 px-3 py-1.5 fw-medium" style="font-size: 0.75rem;">
                                                <i class="bi bi-check2-all me-1"></i> Paid & Settled
                                            </span>
                                        </td>
                                    </tr>
                        <%
                                }
                            } else {
                        %>
                                <tr>
                                    <td colspan="4" class="text-center text-secondary py-5">
                                        <div class="mb-2"><i class="bi bi-folder-x fs-2 text-muted"></i></div>
                                        <p class="mb-0 fw-medium fs-6">No historical sales data found matching the archive parameters.</p>
                                        <small class="text-white-50">When current floor tabs are fully checked out and cleared, they will display here permanently.</small>
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