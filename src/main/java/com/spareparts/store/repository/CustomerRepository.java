package com.spareparts.store.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerRepository {
    public void get() {
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from customers;")) {

            while (rs.next()) {
                System.out.println("id = " + rs.getLong("id"));
                System.out.println("email = " + rs.getString("email"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
