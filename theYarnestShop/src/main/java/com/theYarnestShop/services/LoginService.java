package com.theYarnestShop.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.theYarnestShop.config.DatabaseConfig;
import com.theYarnestShop.model.UserModel;
import com.theYarnestShop.util.PasswordUtil;

public class LoginService {
	private Connection databaseConfig;
	private boolean isConnectionError = false;
	
	public LoginService() {
		try {
			databaseConfig = DatabaseConfig.getDatabaseConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
    }
	
	/**
	 * Validates the user credentials against the database records.
	 *
	 * @param studentModel the StudentModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	public Boolean loginUser(UserModel user) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT user_name, password FROM user WHERE user_name = ?";
		try (PreparedStatement stmt = databaseConfig.prepareStatement(query)) {
			stmt.setString(1, user.getUser_name());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return false;
	}
	
	/**
	 * Validates the password retrieved from the database.
	 *
	 * @param result       the ResultSet containing the user name and password from
	 *                     the database
	 * @param user the UserModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private boolean validatePassword(ResultSet result, UserModel user) throws SQLException {
		String dbUsername = result.getString("user_name");
		String dbPassword = result.getString("password");

		return dbUsername.equals(user.getUser_name())
				&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(user.getPassword());
	}

}
