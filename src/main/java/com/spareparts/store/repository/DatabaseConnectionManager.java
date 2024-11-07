package com.spareparts.store.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static String url;
    private static String username;
    private static String password;

    // Allows setting database properties, especially useful for testing with Testcontainers
    public static void setUrl(String url) {
        DatabaseConnectionManager.url = url;
    }

    public static void setUsername(String username) {
        DatabaseConnectionManager.username = username;
    }

    public static void setPassword(String password) {
        DatabaseConnectionManager.password = password;
    }

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        if (url == null || username == null || password == null) {
            throw new IllegalStateException("Database connection details are not set.");
        }
        return DriverManager.getConnection(url, username, password);
    }
}
