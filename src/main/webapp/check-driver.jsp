<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>JDBC Driver Check</title>
    <style>
        body { font-family: sans-serif; text-align: center; padding: 50px; background-color: #f4f4f9; }
        .badge { display: inline-block; padding: 15px 30px; border-radius: 8px; color: white; font-size: 20px; font-weight: bold; }
        .success { background-color: #2ecc71; }
        .fail { background-color: #e74c3c; }
    </style>
</head>
<body>

    <h2>MySQL JDBC Driver Diagnostic Check</h2>

    <%
        try {
            // This force-loads the exact driver class from your newly added .jar file
            Class.forName("com.mysql.cj.jdbc.Driver");
    %>
            <div class="badge success">
                🎉 Success! MySQL Driver Loaded Successfully.
            </div>
            <p>Tomcat can see the JAR file inside WEB-INF/lib. You are ready to talk to the database!</p>
    <%
        } catch (ClassNotFoundException e) {
    %>
            <div class="badge fail">
                ❌ Error: Driver Not Found!
            </div>
            <p style="color: #c0392b; font-weight: bold;">
                Reason: <%= e.getMessage() %>
            </p>
            <p>Tomcat cannot find the MySQL Connector file. Double-check that it was pasted cleanly inside your WEB-INF/lib folder.</p>
    <%
        }
    %>

</body>
</html>