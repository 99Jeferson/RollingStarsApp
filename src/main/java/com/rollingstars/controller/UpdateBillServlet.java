package com.rollingstars.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rollingstars.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateBillServlet")
public class UpdateBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Grab parameters sent from our smart view-tab.jsp dropdown form
        String tabIdStr = request.getParameter("tabId");
        String itemIdStr = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");

        if (tabIdStr != null && itemIdStr != null && quantityStr != null) {
            int tabId = Integer.parseInt(tabIdStr);
            int itemId = Integer.parseInt(itemIdStr);
            int quantity = Integer.parseInt(quantityStr);

            Connection conn = null;
            PreparedStatement priceStmt = null;
            PreparedStatement billStmt = null;
            PreparedStatement stockStmt = null;
            PreparedStatement logStmt = null;
            ResultSet rs = null;

            try {
                conn = DBConnection.getConnection();
                // Turn off auto-commit to run this safely as a single multi-query transaction
                conn.setAutoCommit(false);

                // STEP A: Fetch unit price and check real-time stock availability
                String priceSql = "SELECT unit_price, stock_qty FROM inventory WHERE id = ?";
                priceStmt = conn.prepareStatement(priceSql);
                priceStmt.setInt(1, itemId);
                rs = priceStmt.executeQuery();

                if (rs.next()) {
                    int unitPrice = rs.getInt("unit_price");
                    int currentStock = rs.getInt("stock_qty");

                    // Safe guard: check if bar has enough stock remaining
                    if (currentStock >= quantity) {
                        int lineItemTotal = unitPrice * quantity;

                        // STEP B: Update customer's running bill amount
                        String updateBillSql = "UPDATE bar_tabs SET total_bill = total_bill + ? WHERE id = ?";
                        billStmt = conn.prepareStatement(updateBillSql);
                        billStmt.setInt(1, lineItemTotal);
                        billStmt.setInt(2, tabId);
                        billStmt.executeUpdate();

                        // STEP C: Deduct items out of core inventory stock counts
                        String updateStockSql = "UPDATE inventory SET stock_qty = stock_qty - ? WHERE id = ?";
                        stockStmt = conn.prepareStatement(updateStockSql);
                        stockStmt.setInt(1, quantity);
                        stockStmt.setInt(2, itemId);
                        stockStmt.executeUpdate();

                        // STEP D: Write an audit record trail into our inventory log history
                        String logSql = "INSERT INTO inventory_logs (item_id, quantity, transaction_type, performed_by) VALUES (?, ?, 'SALE_DEDUCTION', 'Floor_Bartender')";
                        logStmt = conn.prepareStatement(logSql);
                        logStmt.setInt(1, itemId);
                        logStmt.setInt(2, quantity);
                        logStmt.executeUpdate();

                        // Commit all operations together successfully
                        conn.commit();
                    } else {
                        System.out.println("Transaction Rejected: Insufficient inventory stock!");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Critical Error running stock deduction invoice workflow!");
                e.printStackTrace();
                // If any query fails, undo everything to protect data consistency
                if (conn != null) {
                    try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
                }
            } finally {
                // Clean up open connections resources cleanly
                try {
                    if (rs != null) rs.close();
                    if (priceStmt != null) priceStmt.close();
                    if (billStmt != null) billStmt.close();
                    if (stockStmt != null) stockStmt.close();
                    if (logStmt != null) logStmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
            
            // Redirect the bartender cleanly back to the same view tab dashboard frame
            response.sendRedirect("view-tab?id=" + tabId);
            return;
        }

        response.sendRedirect("dashboard");
    }
}