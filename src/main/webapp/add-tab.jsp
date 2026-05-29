<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars - Open New Tab</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #1a1a24; color: #fff; padding: 40px; }
        .container { max-width: 950px; margin: 0 auto; }
        .form-box { max-width: 500px; margin: 30px auto 0 auto; background: #252538; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); }
        h2 { color: #ffcc00; text-align: center; margin-bottom: 25px; margin-top: 0; }
        label { display: block; margin: 15px 0 5px; color: #bbb; font-weight: 500; }
        input[type="text"], input[type="number"] { width: 100%; padding: 12px; border: 1px solid #444; border-radius: 6px; background: #111; color: #fff; box-sizing: border-box; font-size: 15px; }
        input:focus { border-color: #ffcc00; outline: none; }
        button { width: 100%; padding: 14px; background: #ffcc00; border: none; color: #111; font-weight: bold; font-size: 16px; border-radius: 6px; cursor: pointer; transition: 0.3s; margin-top: 25px; }
        button:hover { background: #e6b800; }
    </style>
</head>
<body>

<div class="container">
    <jsp:include page="header.jsp" />

    <div class="form-box">
        <h2>Open New Guest Tab</h2>
        
        <form action="AddTabServlet" method="POST">
            <label>Guest Name / Table Number:</label>
            <input type="text" name="guestName" placeholder="e.g., Table 4 or John Doe" required>
            
            <label>Initial Bill Amount (UGX):</label>
            <input type="number" name="totalBill" placeholder="e.g., 0" value="0" min="0">
            
            <button type="submit">Open Active Tab</button>
        </form>
    </div>
</div>

</body>
</html>