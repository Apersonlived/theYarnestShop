package com.theYarnestShop.services;

import java.sql.*;
import java.util.*;
import com.theYarnestShop.config.DatabaseConfig;
import com.theYarnestShop.model.ProductModel;

public class AdminService {
    private Connection dbConn;
    private boolean isConnectionError = false;

    public AdminService() {
        try {
            dbConn = DatabaseConfig.getDatabaseConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    // Get monthly sales from order_product table
    public double getMonthlySales() throws SQLException {
        if (isConnectionError) {
            throw new SQLException("Database connection error");
        }

        String query = "SELECT SUM(total_price) FROM order_product " +
                      "WHERE MONTH(order_date) = MONTH(CURRENT_DATE())";
        try (Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getDouble(1) : 0;
        }
    }

    // Get low stock products
    public List<ProductModel> getLowStockProducts() throws SQLException {
        if (isConnectionError) {
            throw new SQLException("Database connection error");
        }

        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT product_id, product_name, stock FROM product WHERE stock < 5";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new ProductModel(
                    rs.getString("product_id"),
                    rs.getString("product_name"),
                    null, null, 0, null, rs.getInt("stock")
                ));
            }
        }
        return products;
    }

    // Get bestsellers from order_product table
    public Map<String, Integer> getBestsellers() throws SQLException {
        if (isConnectionError) {
            throw new SQLException("Database connection error");
        }

        Map<String, Integer> bestsellers = new LinkedHashMap<>();
        String query = "SELECT p.product_name, SUM(op.quantity) as total_sold " +
                      "FROM order_product op " +
                      "JOIN product p ON op.product_id = p.product_id " +
                      "GROUP BY p.product_name ORDER BY total_sold DESC LIMIT 5";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bestsellers.put(rs.getString("product_name"), rs.getInt("total_sold"));
            }
        }
        return bestsellers;
    }

    // Get total user count
    public int getTotalUsersCount() throws SQLException {
        if (isConnectionError) {
            throw new SQLException("Database connection error");
        }

        String query = "SELECT COUNT(*) FROM user";
        try (Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Get yearly revenue from order_product table
    public Map<String, Double> getYearlyRevenue() throws SQLException {
        if (isConnectionError) {
            throw new SQLException("Database connection error");
        }

        Map<String, Double> revenue = new LinkedHashMap<>();
        String query = "SELECT MONTHNAME(order_date) as month, SUM(total_price) as revenue " +
                      "FROM order_product " +
                      "WHERE YEAR(order_date) = YEAR(CURRENT_DATE()) " +
                      "GROUP BY MONTH(order_date)";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                revenue.put(rs.getString("month"), rs.getDouble("revenue"));
            }
        }
        return revenue;
    }

    // Update product stock
    public boolean updateProductStock(String productId, int newStock) throws SQLException {
        if (isConnectionError) {
            return false;
        }

        String query = "UPDATE product SET stock = ? WHERE product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, newStock);
            stmt.setString(2, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Close connection
    public void close() {
        try {
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}