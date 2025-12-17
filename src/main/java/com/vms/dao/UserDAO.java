package com.vms.dao;

import com.vms.model.User;
import java.sql.*;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        // Replace with your actual DB connection
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/your_db_name", "username", "password");
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // hash in real app
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole().name());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Add this method
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id")); // if you have id field
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
