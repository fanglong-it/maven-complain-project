package fpt.uni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fpt.uni.model.Account;
import fpt.uni.model.UserRole;
import fpt.uni.utils.DbContext;

public class AccountDAO {
	private DbContext dbContext;

	public AccountDAO() {
		dbContext = new DbContext();
	}

	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		String sql = "SELECT * FROM Account";

		// Open connection and perform operations in try-with-resources
		try (Connection connection = dbContext.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setUsername(resultSet.getString("username"));
				account.setPassword(resultSet.getString("password"));
				account.setEmail(resultSet.getString("email"));
				account.setGoogleId(resultSet.getString("googleId"));
				account.setRole(resultSet.getString("role"));
				account.setIsActive(resultSet.getBoolean("isActive"));
				account.setCreatedAt(resultSet.getDate("createdAt"));
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}

		return accounts; // Return the list of accounts
	}

	public Account getAccountByUsername(String username) {
		String sql = "SELECT * FROM Account WHERE username = ?";
		Account account = null;

		// Open connection and perform operations in try-with-resources
		try (Connection connection = dbContext.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setUsername(resultSet.getString("username"));
				account.setPassword(resultSet.getString("password")); // Store hashed password securely
				account.setEmail(resultSet.getString("email"));
				account.setGoogleId(resultSet.getString("googleId"));
				account.setRole(resultSet.getString("role"));
				account.setIsActive(resultSet.getBoolean("isActive"));
				account.setCreatedAt(resultSet.getDate("createdAt"));
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}

		return account; // Return the found account
	}

	public Account getAccountByGoogleId(String googleId) {
		String sql = "SELECT * FROM Account WHERE googleId = ?";
		Account account = null;

		// Open connection and perform operations in try-with-resources
		try (Connection connection = dbContext.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, googleId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getLong("id"));
				account.setGoogleId(resultSet.getString("googleId"));
				account.setEmail(resultSet.getString("email"));
				account.setUsername(resultSet.getString("username"));
				account.setRole(resultSet.getString("role"));
				account.setIsActive(resultSet.getBoolean("isActive"));
				account.setCreatedAt(resultSet.getDate("createdAt"));
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}

		return account; // Return the found account
	}

	public void createAccount(Account account) {
		String sql = "INSERT INTO Account (googleId, email, username, role) VALUES (?, ?, ?, ?)";

		// Open connection and perform operations in try-with-resources
		try (Connection connection = dbContext.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, account.getGoogleId());
			statement.setString(2, account.getEmail());
			statement.setString(3, account.getUsername());
			statement.setString(4, account.getRole());

			statement.executeUpdate(); // Execute the insert
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
	}

	public static void main(String[] args) {
		AccountDAO accountDAO = new AccountDAO();
		List<Account> accounts = accountDAO.getAllAccounts();
		for (Account account : accounts) {
			System.out.println(account.toString());
		}
	}

}
