package com.vms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // START DB CONFIG
    // NOTE: You used to configure these according to your local environment
    private static final String URL = "jdbc:mysql://localhost:3306/vms_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    // END DB CONFIG

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
