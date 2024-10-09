package fpt.uni.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbContext {

	// Connection details
	private static final String USER = "sa";
	private static final String PASSWORD = "Cunplong115@";
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=complain-db";

	// Instance variable for the connection
	private Connection connection;

	// Constructor
	public DbContext() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			Logger.getLogger(DbContext.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	// Method to get a new connection
	public Connection getConnection() {
		try {
			// Check if the current connection is null or closed
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (SQLException e) {
			Logger.getLogger(DbContext.class.getName()).log(Level.SEVERE, null, e);
		}
		return connection; // Return the connection (either the old one or a new one)
	}

	// Method to close the connection
	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				connection = null; // Set to null after closing
			} catch (SQLException e) {
				Logger.getLogger(DbContext.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

}
