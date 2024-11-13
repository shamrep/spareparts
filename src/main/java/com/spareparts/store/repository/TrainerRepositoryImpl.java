package com.spareparts.store.repository;

import com.spareparts.store.model.Trainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainerRepositoryImpl implements TrainerRepository {

    private final DataSource dataSource;

    public TrainerRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Trainer> findById(Long id) {

        String sql = "select * from trainers where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(new Trainer(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Trainer by ID " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Trainer> findAll() {

        List<Trainer> trainers = new ArrayList<>();
        String sql = "select * from trainers";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                trainers.add(new Trainer(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving all trainers", e);
        }

        return trainers;
    }

    @Override
    public void save(Trainer trainer) {

        String sql = "insert into trainers(name, email) values(?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, trainer.name());
            preparedStatement.setString(2, trainer.email());
            //todo or use Optional<Trainer>??
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to save trainer: no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving trainer ID = " + trainer.id(), e);
        }

    }

    @Override
    public void update(Trainer trainer) {

        String sql = "update trainers set name = ?, email = ? where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, trainer.name());
            preparedStatement.setString(2, trainer.email());
            preparedStatement.setLong(3, trainer.id());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to update trainer: no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating trainer with ID = " + trainer.id(), e);
        }

    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM trainers WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to delete trainer: no trainer found with ID = " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting trainer with ID = " + id, e);
        }
    }
}
