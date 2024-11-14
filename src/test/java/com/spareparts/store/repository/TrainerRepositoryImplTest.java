package com.spareparts.store.repository;

import com.spareparts.store.model.Trainer;
import com.spareparts.store.repository.jdbc.TrainerRepository;
import com.spareparts.store.repository.jdbc.TrainerRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.spareparts.store.repository.DataSourceManager.getDataSource;
import static com.spareparts.store.repository.LiquibaseRunner.runLiquibaseMigrations;
import static org.junit.jupiter.api.Assertions.*;

public class TrainerRepositoryImplTest {

    static PostgreSQLContainer<?> testPostgreSQLContainer;
    static TrainerRepository testTrainerRepository;
    static DataSource testDataSource;
    static Connection testConnection;

    @BeforeAll
    static void setUp() {

        testPostgreSQLContainer = ContainerManager.getContainer();
        testPostgreSQLContainer.start();

        runLiquibaseMigrations(testPostgreSQLContainer);

        testDataSource = getDataSource(testPostgreSQLContainer);

        testTrainerRepository = new TrainerRepositoryImpl(testDataSource);

        try {
            testConnection = testDataSource.getConnection();
            testConnection.setAutoCommit(false); // Disable auto-commit for rollback control
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set up test transaction", e);
        }
    }

    @AfterAll
    public static void tearDown() {

        try {
            testConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close test connection", e);
        }

        testPostgreSQLContainer.stop();
    }

    @AfterEach
    void rollBackTransaction() {
        try {
            testConnection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to roll back test transaction", e);
        }
    }

    @Test
    void testSaveAndFindById() {
        Trainer trainer = new Trainer(null, "John Doe", "john.doe@example.com");

        // Act: Save the trainer
        Optional<Trainer> savedTrainer = testTrainerRepository.save(trainer);

        // Ensure that the trainer was saved and the ID is generated
        assertTrue(savedTrainer.isPresent(), "Trainer should be saved and return a valid Optional.");

        // Act: Find the trainer by ID
        Optional<Trainer> foundTrainer = testTrainerRepository.findById(savedTrainer.get().id());

        // Assert: Check if the trainer is found by ID
        assertTrue(foundTrainer.isPresent(), "Trainer should be found by ID");

        // Assert: Check if the found trainer has the correct attributes
        assertEquals(savedTrainer.get().id(), foundTrainer.get().id(), "Trainer IDs should match.");
        assertEquals("John Doe", foundTrainer.get().name(), "Trainer names should match.");
        assertEquals("john.doe@example.com", foundTrainer.get().email(), "Trainer emails should match.");
    }

    @Test
    void testFindAll() {
        Trainer trainer1 = new Trainer(null, "Jane Smith", "jane.smith@example.com");
        Trainer trainer2 = new Trainer(null, "Bob Brown", "bob.brown@example.com");

        Optional<Trainer> savedTrainer1 = testTrainerRepository.save(trainer1);
        Optional<Trainer> savedTrainer2 = testTrainerRepository.save(trainer2);

        // Ensure trainers were saved
        assertTrue(savedTrainer1.isPresent(), "Trainer1 should be saved and present.");
        assertTrue(savedTrainer2.isPresent(), "Trainer2 should be saved and present.");

        // Act: Retrieve all trainers
        List<Trainer> trainers = testTrainerRepository.findAll();

        // Assert: Check the size and contents of the trainers list
        assertEquals(2, trainers.size(), "Should find 2 trainers");

        // Assert: Verify that the saved trainers are in the list
        assertTrue(trainers.contains(savedTrainer1.get()), "List should contain savedTrainer1");
        assertTrue(trainers.contains(savedTrainer2.get()), "List should contain savedTrainer2");
    }

    @Test
    void testDelete() {
        Trainer trainer = new Trainer(null, "Alice Green", "alice.green@example.com");

        Optional<Trainer> savedTrainer = testTrainerRepository.save(trainer);

        // Ensure that the trainer was saved and the ID is generated
        assertTrue(savedTrainer.isPresent(), "Trainer should be saved and return a valid Optional.");

        // Act: Delete the trainer
        testTrainerRepository.delete(savedTrainer.get().id());

        // Assert: Check that the trainer was deleted
        Optional<Trainer> result = testTrainerRepository.findById(savedTrainer.get().id());
        assertFalse(result.isPresent(), "Trainer should no longer be present after deletion");
    }
}
