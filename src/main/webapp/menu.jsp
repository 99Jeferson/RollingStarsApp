<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>Rolling Stars Menu</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; padding: 40px; }
        .lounge-container { background: #ffffff; padding: 30px; max-width: 400px; border-radius: 12px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; border-bottom: 2px solid #ecf0f1; padding-bottom: 10px; }
        li { font-size: 18px; color: #34495e; padding: 8px 0; list-style-type: none; }
    </style>
</head>
<body>

    <div class="lounge-container">
        <h1>Rolling Stars Drinks</h1>
        <ul>
            <%
                // 1. Fetch the generic data object and pull it back out as a String List
                List<String> drinks = (List<String>) request.getAttribute("menuItems");

                // 2. Safeguard against empty requests
                if (drinks != null) {
                    // 3. Loop through the list using classic Java loop architecture 
                    for(String drinkName : drinks) {
            %>
                        <li>🍻 <%= drinkName %></li>
            <%
                    } 
                } else {
            %>
                    <li>No items found. Please load the system via the /Menu endpoint.</li>
            <%
                } 
            %>
        </ul>
    </div>

</body>
</html>