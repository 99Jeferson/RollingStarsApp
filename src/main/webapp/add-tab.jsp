<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rolling Stars - Open New Tab</title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-12 col-md-8 col-lg-6">
                
                <div class="mb-3 text-start">
                    <a href="dashboard" class="text-warning text-decoration-none small fw-medium">
                        <i class="bi bi-arrow-left me-1"></i> Back to Live Dashboard
                    </a>
                </div>

                <div class="card shadow rounded-3 text-white" style="background-color: #252538; border: 1px solid #34344d;">
                    <div class="card-header border-bottom border-secondary bg-dark bg-opacity-25 p-4 text-start">
                        <h3 class="fw-bold m-0 text-warning">
                            <i class="bi bi-folder-plus me-2"></i>Initialize Running Tab
                        </h3>
                        <small class="text-secondary">Open a new customer billing profile or register a seating table session.</small>
                    </div>
                    
                    <div class="card-body p-4 text-start">
                        
                        <form action="AddTabServlet" method="POST">
                            
                            <div class="mb-3">
                                <label class="form-label text-secondary small fw-medium">Guest Name or Table Identifier</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-dark border-secondary text-secondary">
                                        <i class="bi bi-person-badge-fill"></i>
                                    </span>
                                    <input type="text" name="guestName" class="form-control bg-dark border-secondary text-white shadow-none" 
                                           placeholder="e.g., Table 4 / Alex Mugisha" required>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label text-secondary small fw-medium">Starting Order Bill Deposit (UGX)</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-dark border-secondary text-success fw-bold">UGX</span>
                                    <input type="number" name="totalBill" class="form-control bg-dark border-secondary text-white shadow-none" 
                                           placeholder="0" min="0" value="0" required>
                                </div>
                            </div>

                            <div class="d-flex gap-3 pt-2">
                                <a href="dashboard" class="btn btn-outline-secondary w-50 fw-semibold text-white">
                                    Cancel
                                </a>
                                <button type="submit" class="btn btn-warning w-50 fw-bold">
                                    Create Session <i class="bi bi-check-circle-fill ms-1"></i>
                                </button>
                            </div>
                            
                        </form>
                        
                    </div>
                </div>

            </div>
        </div>
    </div>

</body>
</html>