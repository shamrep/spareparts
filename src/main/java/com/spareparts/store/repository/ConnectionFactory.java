package com.spareparts.store.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    static final String DB_URL = "jdbc:postgres://localhost/sparepartsdb";
    static final String USER = "root";
    static final String PASS = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
