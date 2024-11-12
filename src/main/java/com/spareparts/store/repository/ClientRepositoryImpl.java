package com.spareparts.store.repository;

import com.spareparts.store.model.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl {
    public List<Client> get() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM customers;")) {

            while (rs.next()) {
                clients.add(new Client(rs.getLong("id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clients;
    }

    public List<Client> findAll() {
        return null;
    }

    public Client getReferenceById(Long id) {
        return null;
    }

    public Client save(Client client) {
        return null;
    }

    public void deleteById(Long id) {
    }
}

