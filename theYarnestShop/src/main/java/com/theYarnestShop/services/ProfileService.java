package com.theYarnestShop.services;

import java.sql.*;
import com.theYarnestShop.config.DatabaseConfig;
import com.theYarnestShop.model.UserModel;
import jakarta.servlet.ServletException;

public class ProfileService {
    private Connection connection;
    
    public ProfileService() throws ServletException {
        try {
            this.connection = DatabaseConfig.getDatabaseConnection();
            if (this.connection == null || this.connection.isClosed()) {
                throw new ServletException("Failed to establish database connection");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException("Database connection error", ex);
        }
    }
    
    public UserModel getUserProfile(int userId) throws SQLException {
        String query = "SELECT user_id, full_name, user_name, email, phone, address FROM users WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return extractUserFromResultSet(result);
                }
            }
        }
        return null;
    }
    
    public boolean updateUserProfile(UserModel user) throws SQLException {
        String query = "UPDATE users SET user_name = ?, email = ?, phone = ?, address = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUser_name());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setInt(5, user.getUser_id());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    private UserModel extractUserFromResultSet(ResultSet result) throws SQLException {
        UserModel user = new UserModel();
        user.setUser_id(result.getInt("user_id"));
        user.setFull_name(result.getString("full_name"));
        user.setUser_name(result.getString("user_name"));
        user.setEmail(result.getString("email"));
        user.setPhone(result.getString("phone"));
        user.setAddress(result.getString("address"));
        return user;
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}