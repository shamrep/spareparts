package com.spareparts.store.repository;

import com.spareparts.store.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    public List<Customer> get() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM customers;")) {

            while (rs.next()) {
                customers.add(new Customer(rs.getLong("id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customers;
    }
}

