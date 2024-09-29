package fpt.uni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fpt.uni.filter.PostFilter;
import fpt.uni.model.Post;
import fpt.uni.utils.DbContext;

public class PostDAO extends DbContext {

	public List<Post> getPosts(PostFilter filter, int page, int pageSize) {
		List<Post> posts = new ArrayList<>();
		String sql = "SELECT p.*, a.username, l.city, l.location " + "FROM Post p "
				+ "JOIN Account a ON p.accountId = a.id " + "JOIN Label l ON p.labelId = l.id " + "WHERE 1=1 ";

		// Build dynamic query based on filters
		if (filter.getLabelId() != null) {
			sql += "AND p.labelId = ? ";
		}
		if (filter.getContent() != null && !filter.getContent().isEmpty()) {
			sql += "AND p.content LIKE ? ";
		}

		sql += "ORDER BY p.createdAt DESC " + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			int parameterIndex = 1;

			// Set parameters
			if (filter.getLabelId() != null) {
				statement.setLong(parameterIndex++, filter.getLabelId());
			}
			if (filter.getContent() != null && !filter.getContent().isEmpty()) {
				statement.setString(parameterIndex++, "%" + filter.getContent() + "%");
			}

			// Set paging parameters
			statement.setInt(parameterIndex++, (page - 1) * pageSize);
			statement.setInt(parameterIndex, pageSize);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Post post = new Post();
					post.setId(resultSet.getLong("id"));
					post.setContent(resultSet.getString("content"));
					post.setCreatedAt(resultSet.getDate("createdAt"));
					post.setLabelId(resultSet.getLong("labelId"));
					post.setAccountId(resultSet.getLong("accountId"));
					post.setStatus(resultSet.getString("status"));
//					post.setAccountId(resultSet.get); // Get username
					posts.add(post);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	public int getTotalPosts(PostFilter filter) {
		int total = 0;
		String sql = "SELECT COUNT(*) FROM Post p " + "JOIN Account a ON p.accountId = a.id "
				+ "JOIN Label l ON p.labelId = l.id " + "WHERE 1=1 ";

		// Build dynamic query based on filters
		if (filter.getLabelId() != null) {
			sql += "AND p.labelId = ? ";
		}
		if (filter.getContent() != null && !filter.getContent().isEmpty()) {
			sql += "AND p.content LIKE ? ";
		}

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			int parameterIndex = 1;

			// Set parameters
			if (filter.getLabelId() != null) {
				statement.setLong(parameterIndex++, filter.getLabelId());
			}
			if (filter.getContent() != null && !filter.getContent().isEmpty()) {
				statement.setString(parameterIndex++, "%" + filter.getContent() + "%");
			}

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					total = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public void createPost(Post post) {
		String sql = "INSERT INTO Post (accountId, labelId, content, status, createdAt, updatedAt) VALUES (?, ?, ?, ?, GETDATE(), GETDATE())";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setLong(1, post.getAccountId());
			statement.setLong(2, post.getLabelId());
			statement.setString(3, post.getContent());
			statement.setString(4, "PENDING"); // Default status

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exception appropriately
		}
	}

	public Post getPostById(long id) {
		Post post = null;
		String sql = "SELECT p.*, a.username, l.city, l.location " + "FROM Post p "
				+ "JOIN Account a ON p.accountId = a.id " + "JOIN Label l ON p.labelId = l.id " + "WHERE p.id = ?";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				post = new Post();
				post.setId(resultSet.getLong("id"));
				post.setAccountId(resultSet.getLong("accountId"));
				post.setLabelId(resultSet.getLong("labelId"));
				post.setContent(resultSet.getString("content"));
				post.setStatus(resultSet.getString("status"));
				post.setCreatedAt(resultSet.getDate("createdAt"));
				post.setUpdatedAt(resultSet.getDate("updatedAt"));
				post.setDeletedAt(resultSet.getDate("deletedAt"));

				// Add additional fields from the joined tables if needed
				post.setUsername(resultSet.getString("username"));
				post.setCity(resultSet.getString("city"));
				post.setLocation(resultSet.getString("location"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return post;
	}

	public void updatePostStatus(long postId, String status, String comment, long moderatorId) {
		String updateSql = "UPDATE Post SET status = ? WHERE id = ?";
		String insertSql = "INSERT INTO Approval (postId, adminId, status, approvalTime, comment) VALUES (?, ?, ?, GETDATE(), ?)";

		try (Connection connection = getConnection();
				PreparedStatement updateStatement = connection.prepareStatement(updateSql);
				PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

			// Update post status
			updateStatement.setString(1, status);
			updateStatement.setLong(2, postId);
			updateStatement.executeUpdate();

			// Insert into Approval table
			insertStatement.setLong(1, postId);
			insertStatement.setLong(2, moderatorId); // Admin ID
			insertStatement.setString(3, status);
			insertStatement.setString(4, comment);
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
