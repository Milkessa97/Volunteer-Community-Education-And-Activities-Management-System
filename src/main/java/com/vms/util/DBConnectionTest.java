package com.vms.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) {
        System.out.println("Attempting DB connection...");
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("DB connection successful: " + conn.getMetaData().getURL());
            } else {
                System.err.println("DB connection returned null or closed.");
            }
        } catch (SQLException e) {
            System.err.println("DB connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
