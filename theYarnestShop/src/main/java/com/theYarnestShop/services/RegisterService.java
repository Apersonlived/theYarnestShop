package com.theYarnestShop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.theYarnestShop.config.DatabaseConfig;
import com.theYarnestShop.model.UserModel;

public class RegisterService {
	private Connection databaseConn;

	public RegisterService() {
		try {
			this.databaseConn = DatabaseConfig.getDatabaseConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("There was an error with your database connection. Error: " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	/**
	 * Checks if user name already exists in the database
	 */
	public boolean isUsernameExists(String user_name) {
		if (databaseConn == null)
			return false;

		String sql = "SELECT COUNT(*) FROM user WHERE user_name = ?";

		try (PreparedStatement stmt = databaseConn.prepareStatement(sql)) {
			stmt.setString(1, user_name);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking username: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Checks email already exists in the database
	 */
	public boolean isEmailExists(String email) {
		if (databaseConn == null)
			return false;

		String sql = "SELECT COUNT(*) FROM user WHERE email = ?";

		try (PreparedStatement stmt = databaseConn.prepareStatement(sql)) {
			stmt.setString(1, email);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking email: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Checks if phone number already exists in the database
	 */
	public boolean isPhoneExists(String phone) {
		if (databaseConn == null)
			return false;

		String sql = "SELECT COUNT(*) FROM user WHERE phone= ?";

		try (PreparedStatement stmt = databaseConn.prepareStatement(sql)) {
			stmt.setString(1, phone);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking phone: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public Boolean addUser(UserModel user) {
		if (databaseConn == null) {
			System.err.println("There is no database connection available.");
			return null;
		}

		System.out.println("Attempting to register user: " + user.getUser_name() + ", " + user.getEmail());

		if (databaseConn == null) {
			System.err.println("Database connection is null!");
			return false;
		}

		// First check if user name or email already exists
		if (isUsernameExists(user.getUser_name())) {
			System.err.println("Username already exists: " + user.getUser_name());
			return false;
		}
		if (isEmailExists(user.getEmail())) {
			System.err.println("Email already exists: " + user.getEmail());
			return false;
		}
		if (isPhoneExists(user.getPhone())) {
			System.err.println("Phone number already exists: " + user.getPhone());
			return false;
		}

		String insertQuery = "INSERT INTO user (full_name, user_name, email, phone, address, password, role) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement insertStmt = databaseConn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			// Insert student details
			insertStmt.setString(1, user.getFull_name());
			insertStmt.setString(2, user.getUser_name());
			insertStmt.setString(3, user.getEmail());
			insertStmt.setString(4, user.getPhone());
			insertStmt.setString(5, user.getAddress());
			insertStmt.setString(6, user.getPassword());
			insertStmt.setString(7, user.getRole());

            int rowsAffected = insertStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Registration successful! Rows affected: " + rowsAffected);
                // Get the auto-generated user ID
                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUser_id(generatedKeys.getInt(1));
                        System.out.println("Generated user ID: " + user.getUser_id());
                    }
                }
                return true;
            }
            System.out.println("No rows affected by INSERT");
            return false;
		} catch (SQLException e) {
			System.err.println("Error during student registration: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
