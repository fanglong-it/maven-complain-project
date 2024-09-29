package fpt.uni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fpt.uni.model.Label;
import fpt.uni.utils.DbContext;

public class LabelDAO extends DbContext {

    public List<Label> getAllLabels() {
        List<Label> labels = new ArrayList<>();
        String sql = "SELECT * FROM Label";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Label label = new Label();
                label.setId(resultSet.getLong("id"));
                label.setCity(resultSet.getString("city"));
                label.setLocation(resultSet.getString("location"));
                labels.add(label);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }
}