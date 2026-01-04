package com.vms.dao;

import com.vms.model.User;
import com.vms.util.DBUtil; // Import your utility
import java.sql.*;

public class UserDAO {

    // Helper method to get the connection from your new DBUtil
    private Connection getConnection() throws SQLException {
        return DBUtil.getConnection();
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        // Use try-with-resources to ensure connection closes
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRoleId(rs.getLong("role_id"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}