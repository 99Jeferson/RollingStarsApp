<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars - Book a Table</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; padding: 40px; }
        .form-box { background: white; padding: 30px; max-width: 400px; border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h2 { color: #2c3e50; margin-bottom: 20px; }
        .input-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background: #d35400; color: white; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; width: 100%; font-size: 16px; }
        button:hover { background: #e67e22; }
    </style>
</head>
<body>

    <div class="form-box">
        <h2>VIP Table Booking</h2>
        
        <form action="ReservationServlet" method="POST">
            <div class="input-group">
                <label>Customer Name:</label>
                <input type="text" name="customerName" required>
            </div>
            
            <div class="input-group">
                <label>Number of Guests:</label>
                <input type="number" name="guestCount" required>
            </div>
            
            <button type="submit">Confirm Reservation</button>
        </form>
    </div>

</body>
</html>