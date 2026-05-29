package com.rollingstars.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // The connection URL pointing to your local Ubuntu MySQL instance on default port 3306
    private static final String URL = "jdbc:mysql://localhost:3306/rolling_stars_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "jeff_dev";
    private static final String PASSWORD = ",3gGUsT.G@7j,Gi@26";

    /**
     * Establishes and returns a live connection to the rolling_stars_dbr database.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Force load the driver class we just verified
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver missing in runtime path!");
            e.printStackTrace();
        }
        
        // Open and return the pipeline to MySQL
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}