package com.spareparts.store.repository;

import com.spareparts.store.model.Trainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static com.spareparts.store.repository.DataSourceManager.getDataSource;
import static com.spareparts.store.repository.LiquibaseRunner.runLiquibaseMigrations;
import static org.junit.jupiter.api.Assertions.*;

public class TrainerRepositoryImplTest {

    static PostgreSQLContainer<?> postgreSQLContainer;
    static TrainerRepository trainerRepository;
    static DataSource dataSource;

    @BeforeAll
    static void setUp() {

        postgreSQLContainer = ContainerManager.getContainer();
        postgreSQLContainer.start();

        runLiquibaseMigrations(postgreSQLContainer);

        dataSource = getDataSource(postgreSQLContainer);

        trainerRepository = new TrainerRepositoryImpl(dataSource);
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @AfterEach
    void cleanUp() {
        // Optional: Clear the trainers table after each test for isolation
        trainerRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        Trainer trainer = new Trainer(1L,"John Doe", "john.doe@example.com");

        // Act: Save the trainer
        trainerRepository.save(trainer);

        // Assert: Retrieve the trainer by ID and check fields
        Optional<Trainer> result = trainerRepository.findById(trainer.id());

        assertTrue(result.isPresent(), "Trainer should be found by ID");
        assertEquals("John Doe", result.get().name());
        assertEquals("john.doe@example.com", result.get().email());
    }

    @Test
    void testFindAll() {
        Trainer trainer1 = new Trainer(1L, "Jane Smith", "jane.smith@example.com");
        Trainer trainer2 = new Trainer(2L, "Bob Brown", "bob.brown@example.com");

        trainerRepository.save(trainer1);
        trainerRepository.save(trainer2);

        // Act: Retrieve all trainers
        List<Trainer> trainers = trainerRepository.findAll();

        // Assert: Check the size and contents of the trainers list
        assertEquals(2, trainers.size(), "Should find 2 trainers");
        assertTrue(trainers.contains(trainer1));
        assertTrue(trainers.contains(trainer2));
    }

    @Test
    void testDelete() {
        Trainer trainer = new Trainer(1L, "Alice Green", "alice.green@example.com");
        trainerRepository.save(trainer);

        // Act: Delete the trainer
        trainerRepository.delete(trainer.id());

        // Assert: Check that the trainer was deleted
        Optional<Trainer> result = trainerRepository.findById(trainer.id());
        assertFalse(result.isPresent(), "Trainer should no longer be present after deletion");
    }
}
