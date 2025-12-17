package com.vms.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.vms.model.User;
import com.vms.util.DBUtil;
public class UserDAO {
    public User findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
        }
        return null;
    }
}