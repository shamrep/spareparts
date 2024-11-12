package com.spareparts.store.repository;

import com.spareparts.store.model.Trainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TrainerRepositoryImpl implements TrainerRepository {

    private ConnectionManager connectionManager;

    @Override
    public Optional<Trainer> findById(Long id) {
        String sql = "select * from trainers where id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Trainer(resultSet.getString("name"),
                            resultSet.getString("email")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Trainer> findAll() {
        return List.of();
    }

    @Override
    public void save(Trainer customer) {

    }

    @Override
    public void update(Trainer customer) {

    }

    @Override
    public void delete(Long id) {

    }
}
