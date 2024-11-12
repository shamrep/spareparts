package com.spareparts.store.repository;

import com.spareparts.store.model.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {
    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM clients;")) {

            while (rs.next()) {
                clients.add(new Client(rs.getLong("id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clients;
    }

    @Override
    public void save(Client client) {

    }

    @Override
    public void update(Client client) {

    }

    @Override
    public void delete(Long id) {

    }


}

