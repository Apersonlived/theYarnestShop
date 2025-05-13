package com.theYarnestShop.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
	//Information for configuring the database
	private static final String DB_NAME = "the_yarnest_shop";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	public static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	public static String getDbName() {
		return DB_NAME;
	}		
}
