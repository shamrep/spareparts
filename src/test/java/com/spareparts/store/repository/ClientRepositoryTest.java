package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.repository.util.DatabaseTestManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

public class ClientRepositoryTest {
    private static DatabaseTestManager databaseTestManager;
    private static JdbcClient jdbcClient;
    private static ClientRepository clientRepository;

    @BeforeAll
    static void setUp() {

        databaseTestManager = new DatabaseTestManager();
        databaseTestManager.startContainer();
        databaseTestManager.runLiquibaseMigration();
        databaseTestManager.setConnectionAutoCommit(false);
        jdbcClient = databaseTestManager.getJdbcClient();
        clientRepository = new ClientRepository(jdbcClient);
    }

    @AfterAll
    static void tearDown() {

        databaseTestManager.stopContainer();
    }

    @AfterEach
    void rollbackTransaction() {

        databaseTestManager.rollbackTransaction();
    }

    @Test
    void testSaveAndFindById() {
        // Arrange: Create a ClientEntity object for saving
        ClientEntity newClient = new ClientEntity(
                0L, // ID will be generated
                "test@example.com",
                "Test User",
                "hashed_password123"
        );

        // Act: Save the new client
        long generatedId = clientRepository.save(newClient);

        // Assert: Ensure the ID was returned
        Assertions.assertThat(generatedId).isGreaterThan(0);

        newClient = new ClientEntity(
                generatedId, // ID will be generated
                "test@example.com",
                "Test User",
                "hashed_password123"
        );

        // Act: Retrieve the saved client using `findById`
        Optional<ClientEntity> foundClientOptional = clientRepository.findById(generatedId);

        // Assert: Ensure the client is found
        Assertions.assertThat(foundClientOptional).isPresent();

        // Assert: Verify the client data matches the saved data
        ClientEntity foundClient = foundClientOptional.get();
        Assertions.assertThat(foundClient.getId()).isEqualTo(generatedId);
        Assertions.assertThat(foundClient.getEmail()).isEqualTo(newClient.getEmail());
        Assertions.assertThat(foundClient.getName()).isEqualTo(newClient.getName());
        Assertions.assertThat(foundClient.getPassword()).isEqualTo(newClient.getPassword());

        Assertions.assertThat(foundClient).isEqualTo(newClient);
    }

}
