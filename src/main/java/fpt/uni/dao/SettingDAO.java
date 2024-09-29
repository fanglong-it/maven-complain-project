package fpt.uni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import fpt.uni.utils.DbContext;

public class SettingDAO extends DbContext {

	// Method to get the latest settings as a Map
	public Map<String, String> getLatestSettings() {
		Map<String, String> settingsMap = new HashMap<>();
		String sql = "SELECT [key], value FROM Setting";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				String key = resultSet.getString("key");
				String value = resultSet.getString("value");
				settingsMap.put(key, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return settingsMap;
	}
	
	public void updateSetting(String key, String value) {
	    String sql = "UPDATE Setting SET value = ? WHERE [key] = ?";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, value);
	        statement.setString(2, key);
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
