<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars - Add Guest Tab</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #1a1a24; color: #fff; padding: 40px; }
        .container { max-width: 400px; margin: 0 auto; background: #252538; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); }
        h2 { text-align: center; color: #ffcc00; margin-bottom: 20px; }
        label { display: block; margin: 10px 0 5px; color: #bbb; }
        input[type="text"], input[type="number"] { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #444; border-radius: 6px; background: #111; color: #fff; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background: #ffcc00; border: none; color: #111; font-weight: bold; font-size: 16px; border-radius: 6px; cursor: pointer; transition: 0.3s; }
        button:hover { background: #e6b800; }
    </style>
</head>
<body>

<div class="container">
    <h2>Open New Bar Tab</h2>
    <form action="AddTabServlet" method="POST">
        <label>Guest Name:</label>
        <input type="text" name="guestName" placeholder="e.g., Jeferson Clinton" required>
        
        <label>Initial Bill Amount (UGX):</label>
        <input type="number" name="totalBill" placeholder="e.g., 50000" required>
        
        <button type="submit">Save Tab to Database</button>
    </form>
</div>

</body>
</html>