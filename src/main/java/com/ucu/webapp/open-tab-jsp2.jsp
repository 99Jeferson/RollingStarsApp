<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars - Open a Tab</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; padding: 40px; }
        .card { background: white; padding: 30px; max-width: 400px; border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        input, button { width: 100%; padding: 10px; margin-top: 10px; border-radius: 4px; box-sizing: border-box; }
        button { background: #2c3e50; color: white; border: none; font-size: 16px; cursor: pointer; }
    </style>
</head>
<body>

    <div class="card">
        <h2>Welcome to Rolling Stars</h2>
        <p>Enter your name to open a running bar tab:</p>
        <form action="TabServlet" method="POST">
            <input type="text" name="guestName" placeholder="Your Name" required>
            <button type="submit">Open Active Tab</button>
        </form>
    </div>

</body>
</html>