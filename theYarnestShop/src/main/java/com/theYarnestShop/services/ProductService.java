package com.theYarnestShop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.theYarnestShop.config.DatabaseConfig;
import com.theYarnestShop.model.ProductModel;

import jakarta.servlet.ServletException;

public class ProductService {
    private Connection connection;
    
    public ProductService() throws ServletException {
        try {
            this.connection = DatabaseConfig.getDatabaseConnection();
            if (this.connection == null || this.connection.isClosed()) {
                throw new ServletException("Failed to establish database connection");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException("Database connection error", ex);
        }
    }
    
    public List<ProductModel> getAllProducts() throws SQLException {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT product_id, product_name, category, description, price, image, stock FROM product";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {
            
            while (result.next()) {
                products.add(extractProductFromResultSet(result));
            }
        }
        return products;
    }
    
    public ProductModel getProductById(String id) throws SQLException {
        String query = "SELECT product_id, product_name, category, description, price, image, stock FROM product WHERE product_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return extractProductFromResultSet(result);
                }
            }
        }
        return null;
    }
    
    public boolean addProduct(ProductModel product) throws SQLException {
        String query = "INSERT INTO product (product_id, product_name, category, description, price, image, stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getProduct_id());
            stmt.setString(2, product.getProduct_name());
            stmt.setString(3, product.getCategory());
            stmt.setString(4, product.getDescription());
            stmt.setFloat(5, product.getPrice());
            stmt.setString(6, product.getImage());
            stmt.setInt(7, product.getStock());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateProduct(ProductModel product) throws SQLException {
        String query = "UPDATE product SET product_name = ?, category = ?, description = ?, price = ?, image = ?, stock = ? WHERE product_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getProduct_name());
            stmt.setString(2, product.getCategory());
            stmt.setString(3, product.getDescription());
            stmt.setFloat(4, product.getPrice());
            stmt.setString(5, product.getImage());
            stmt.setInt(6, product.getStock());
            stmt.setString(7, product.getProduct_id());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteProduct(String id) throws SQLException {
        String query = "DELETE FROM product WHERE product_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    private ProductModel extractProductFromResultSet(ResultSet result) throws SQLException {
        ProductModel product = new ProductModel();
        product.setProduct_id(result.getString("product_id"));
        product.setProduct_name(result.getString("product_name"));
        product.setCategory(result.getString("category"));
        product.setDescription(result.getString("description"));
        product.setPrice(result.getFloat("price"));
        product.setImage(result.getString("image"));
        product.setStock(result.getInt("stock"));
        return product;
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