package com.spareparts.store.repository;

import com.spareparts.store.model.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.spareparts.store.repository.DataSourceManager.getDataSource;
import static com.spareparts.store.repository.LiquibaseRunner.runLiquibaseMigrations;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@Testcontainers
public class ClientRepositoryTest {

    //    @Container
    static PostgreSQLContainer<?> postgreSQLContainer;

    static ClientRepository clientRepository;

    static DataSource dataSource;

    @BeforeAll
    static void setUp() {

        postgreSQLContainer = ContainerManager.getContainer();
        postgreSQLContainer.start();

        runLiquibaseMigrations(postgreSQLContainer);

        dataSource = getDataSource(postgreSQLContainer);

        DatabaseConnectionManager.setUrl(postgreSQLContainer.getJdbcUrl());
        DatabaseConnectionManager.setUsername(postgreSQLContainer.getUsername());
        DatabaseConnectionManager.setPassword(postgreSQLContainer.getPassword());

        // Initialize the repository
        clientRepository = new ClientRepositoryImpl();

    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void findAllTest() {

        // insert test data
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO clients (email) VALUES ('test1@gmail.com')");
            statement.execute("INSERT INTO clients (email) VALUES ('test2@gmail.com')");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set up test data", e);
        }

        List<Client> customers = clientRepository.findAll();

        assertEquals(2, customers.size());
        assertEquals("test1@gmail.com", customers.get(0).getEmail());
        assertEquals("test2@gmail.com", customers.get(1).getEmail());

    }

}
